package com.ticketmonster.ticketbeast.controllers;


import com.ticketmonster.ticketbeast.exceptions.ResourceNotFoundException;
import com.ticketmonster.ticketbeast.models.User;
import com.ticketmonster.ticketbeast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user_api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
    }

    // Create a new User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    // Update a User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        user.setFirst_name(userDetails.getFirst_name());
        user.setLast_name(userDetails.getLast_name());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
