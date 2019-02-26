package mo.springrestful.controllers;

import mo.springrestful.exceptions.UserNotFoundException;
import mo.springrestful.models.User;
import mo.springrestful.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public User retrieveUser(@PathVariable int id) {

        User user = userDaoService.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id = "+id);
        return user;
    }



    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

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