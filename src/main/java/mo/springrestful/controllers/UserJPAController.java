package mo.springrestful.controllers;

import mo.springrestful.exceptions.UserNotFoundException;
import mo.springrestful.models.Post;
import mo.springrestful.models.User;
import mo.springrestful.services.PostRepository;
import mo.springrestful.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/JPA/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/JPA/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = "+id);

        Resource<User> userResource = new Resource<User>(user.get());
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(this.getClass())
                .retrieveAllUsers()
        );
        userResource.add(linkBuilder.slash(user.get().getId()).withSelfRel());
        userResource.add(linkBuilder.withRel("all-users"));
        return userResource;
    }



    @PostMapping("/JPA/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {

        User save = userRepository.save(user);

        URI Location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(Location).build();
    }


    @DeleteMapping("/JPA/users/{id}")
    public void deleteUser(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = "+id);
        userRepository.delete(user.get());
    }


/*
    @GetMapping("/users/filter/{name}/{id}")
    public MappingJacksonValue retrieveUserAfterFiltering(@PathVariable String name,@PathVariable int id) {

        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = "+id);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(name);
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("GetSpecialField",simpleBeanPropertyFilter);
        MappingJacksonValue map = new MappingJacksonValue(user);
        map.setFilters(filterProvider);
        Resource<MappingJacksonValue> userResource = new Resource<>(map);
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(this.getClass())
                .retrieveAllUsersAfterFiltering(name)
        );
        userResource.add(linkBuilder.slash(user.getId()).withSelfRel());
        userResource.add(linkBuilder.withRel("all-users"));
        List<Link> link = userResource.getLinks();
        System.out.println(link.get(0).toString());
        System.out.println(link.get(1).toString());
        return map;
    }

    @GetMapping("/users/filter/{name}")
    public MappingJacksonValue retrieveAllUsersAfterFiltering(@PathVariable String name) {
        List<User> list = userDaoService.findAll();
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.
                filterOutAllExcept(name.toLowerCase());
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("GetSpecialField",simpleBeanPropertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }
*/

    @GetMapping("/JPA/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id)
    {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id = "+id);

        return user.get().getPosts();
    }

    @GetMapping("/JPA/users/{id}/posts/{postid}")
    public Resource<Post> retrievePost(@PathVariable int id,@PathVariable int postid) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id of user = "+id);

        Iterator<Post> iterator = user.get().getPosts().iterator();
        Post p = null;
        while (iterator.hasNext())
        {
            p = iterator.next();
            if ( p.getId() == postid )
                break;
        }
        if (p == null || p.getId() != postid)
            throw new UserNotFoundException("id of post = "+id);

        Resource<Post> userResource = new Resource<Post>(p);
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(this.getClass())
                .retrieveAllPosts(id)
        );
        userResource.add(linkBuilder.slash(p.getId()).withSelfRel());
        userResource.add(linkBuilder.withRel("all-users"));
        return userResource;
    }



    @PostMapping("/JPA/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id ,@RequestBody Post post) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id of user = "+id);

        post.setUser(user.get());
        postRepository.save(post);

        URI Location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(Location).build();
    }


    @DeleteMapping("/JPA/users/{id}/posts/{postid}")
    public void deletePost(@PathVariable int id,@PathVariable int postid) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("id of user = "+id);

        Iterator<Post> iterator = user.get().getPosts().iterator();
        Post post = null;
        while (iterator.hasNext())
        {
            post = iterator.next();
            if ( post.getId() == postid )
                break;
        }
        if (post == null || post.getId() != postid)
            throw new UserNotFoundException("id of post = "+id);

        postRepository.delete(post);
    }


}