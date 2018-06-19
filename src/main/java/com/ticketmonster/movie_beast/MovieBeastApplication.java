package com.ticketmonster.movie_beast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieBeastApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieBeastApplication.class, args);
    }
}
