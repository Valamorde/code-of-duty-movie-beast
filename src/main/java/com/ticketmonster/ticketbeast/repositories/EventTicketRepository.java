package com.ticketmonster.ticketbeast.repositories;

import com.ticketmonster.ticketbeast.models.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTicketRepository extends JpaRepository<EventTicket, Long> {
}
