package com.ticketmonster.deprecated_logic_for_reference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketBeastApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketBeastApplication.class, args);
    }
}
