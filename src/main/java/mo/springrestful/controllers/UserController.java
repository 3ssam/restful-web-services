package mo.springrestful.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import mo.springrestful.exceptions.UserNotFoundException;
import mo.springrestful.models.User;
import mo.springrestful.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {

        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = "+id);

        Resource<User> userResource = new Resource<>(user);
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
                .methodOn(this.getClass())
                .retrieveAllUsers()
        );
        userResource.add(linkBuilder.slash(user.getId()).withSelfRel());
        userResource.add(linkBuilder.withRel("all-users"));
        return userResource;
    }



    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {

        User save = userDaoService.save(user);

        URI Location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(Location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {

        User user = userDaoService.delete(id);

        if (user == null)
            throw new UserNotFoundException("id = "+id);
    }


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


}