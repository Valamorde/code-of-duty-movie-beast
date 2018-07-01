package com.ticketmonster.movie_beast.initializers;

import com.ticketmonster.movie_beast.models.Role;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class UserInitializer {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    @Transactional
    public void init(){
        User user = new User();
        user.setEmail("user@dummy.com");
        user.setFullName("User Userson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.USER.name());
        user.setEnabled(true);
        userRepository.save(user);

        user = new User();
        user.setEmail("admin@dummy.com");
        user.setFullName("Admin Adminson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.ADMIN.name());
        user.setEnabled(true);
        userRepository.save(user);
    }
}
