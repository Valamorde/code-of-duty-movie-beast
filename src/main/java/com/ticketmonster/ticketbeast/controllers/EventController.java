package com.ticketmonster.ticketbeast.controllers;


import com.ticketmonster.ticketbeast.exceptions.ResourceNotFoundException;
import com.ticketmonster.ticketbeast.models.Event;
import com.ticketmonster.ticketbeast.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/event_api")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    // Get All Events
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get a Single Event
    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable(value = "id") Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "Id", id));
    }

    // Create a new Event
    @PostMapping("/events")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    // Update a Event
    @PutMapping("/events/{id}")
    public Event updateEvent(@PathVariable(value = "id") Long id, @Valid @RequestBody Event eventDetails) {
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
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "id") Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", "Id", id));

        eventRepository.delete(event);

        return ResponseEntity.ok().build();
    }
}
