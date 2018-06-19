package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.models.Role;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.security.Principal;

@Component
@RestController(value = "/user")
public class UserAuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // Logs in an existing user by email/password
    @PostMapping("/login")
    public Principal user(Principal user) {

        return user;
    }

    // Registers a new User
    @PostMapping("/register")
    @Produces("application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            return new ResponseEntity<>(
                    new CustomException("Sorry but email: <" + newUser.getEmail() + "> already exists."),
                    HttpStatus.CONFLICT);
        }
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER.name());
        newUser.setEnabled(true);
        return new ResponseEntity<User>(userRepository.save(newUser), HttpStatus.CREATED);
    }
}
