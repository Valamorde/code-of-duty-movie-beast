package com.ticketmonster.deprecated_logic_for_reference.repositories;

import com.ticketmonster.deprecated_logic_for_reference.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
