package com.ticketmonster.movie_beast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan
@CrossOrigin
public class MovieBeastApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieBeastApplication.class, args);
    }
}
