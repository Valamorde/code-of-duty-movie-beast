package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController("/users")
public class UserManipulationController {

    @Autowired
    IUserRepository userRepository;

    // Get All Users
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
    }

    // Update a User
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id") Integer id, @Valid @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        user.setFull_name(userDetails.getFull_name());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
