package mo.springrestful.controllers;

import mo.springrestful.exceptions.UserNotFoundException;
import mo.springrestful.models.User;
import mo.springrestful.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
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


}