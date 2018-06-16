package com.ticketmonster.ticketbeast.repositories;

import com.ticketmonster.ticketbeast.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
