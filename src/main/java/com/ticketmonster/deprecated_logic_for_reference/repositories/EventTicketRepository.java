package com.ticketmonster.deprecated_logic_for_reference.repositories;

import com.ticketmonster.deprecated_logic_for_reference.models.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTicketRepository extends JpaRepository<EventTicket, Integer> {

    @Query("SELECT et FROM EventTicket et WHERE et.user = :user_id")
    List<EventTicket> findByUserId(@Param("user_id") Integer user_id);
}
