package com.ticketmonster.ticketbeast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.ticketmonster.ticketbeast.controllers"
        , "com.ticketmonster.ticketbeast.repositories"
        , "com.ticketmonster.ticketbeast.models"
        , "com.ticketmonster.ticketbeast.initializers"
        , "com.ticketmonster.ticketbeast.exceptions"
        , "com.ticketmonster.ticketbeast.config"})
public class TicketBeastApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketBeastApplication.class, args);
    }
}
