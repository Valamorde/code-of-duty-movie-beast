package com.ticketmonster.deprecated_logic_for_reference.controllers;


import com.ticketmonster.deprecated_logic_for_reference.exceptions.ResourceNotFoundException;
import com.ticketmonster.deprecated_logic_for_reference.models.Event;
import com.ticketmonster.deprecated_logic_for_reference.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // Get All Events
    @GetMapping("/events")
    @Produces("application/json")
    public List<Event> getAllEvents() {
        System.out.println("getting all events");
        return eventRepository.findAll();
    }

    // Get a Single Event
    @GetMapping("/events/{id}")
    @Produces("application/json")
    public Event getEventById(@PathVariable(value = "id") Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "Id", id));
    }

    // Create a new Event
    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    // Update a Event
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable(value = "id") Integer id, @Valid @RequestBody Event eventDetails) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "Id", id));
        event.setName(eventDetails.getName());
        event.setLocation(eventDetails.getLocation());
        event.setDuration(eventDetails.getDuration());
        event.setPrice(eventDetails.getPrice());
        event.setEvent_date(eventDetails.getEvent_date());

        Event updatedEvent = eventRepository.save(event);
        return updatedEvent;
    }

    // Delete a Event
    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "id") Integer id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "Id", id));

        eventRepository.delete(event);

        return ResponseEntity.ok().build();
    }
}
