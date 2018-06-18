package com.ticketmonster.ticketbeast.controllers;

import com.ticketmonster.ticketbeast.exceptions.CustomException;
import com.ticketmonster.ticketbeast.exceptions.ResourceNotFoundException;
import com.ticketmonster.ticketbeast.models.Role;
import com.ticketmonster.ticketbeast.models.User;
import com.ticketmonster.ticketbeast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // Login
    @CrossOrigin
    @RequestMapping("/login")
    public Principal user(Principal user) {
        System.out.println(user.toString() + "logging in");
        return user;
    }

    // Register a new User
    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            return new ResponseEntity<>(
                    new CustomException("Sorry, a user with the email: " + newUser.getEmail() + "already exists. "),
                    HttpStatus.CONFLICT);
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setEnabled(true);
        return new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.CREATED);
    }

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
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
