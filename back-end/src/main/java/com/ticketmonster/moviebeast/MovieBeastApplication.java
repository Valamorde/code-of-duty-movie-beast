package com.ticketmonster.moviebeast;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableScheduling
@ComponentScan
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@JsonAutoDetect
public class MovieBeastApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieBeastApplication.class, args);
    }
}
