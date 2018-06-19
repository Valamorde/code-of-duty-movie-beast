package com.ticketmonster.deprecated_logic_for_reference.controllers;

import com.ticketmonster.deprecated_logic_for_reference.exceptions.CustomException;
import com.ticketmonster.deprecated_logic_for_reference.models.Event;
import com.ticketmonster.deprecated_logic_for_reference.models.EventTicket;
import com.ticketmonster.deprecated_logic_for_reference.models.User;
import com.ticketmonster.deprecated_logic_for_reference.repositories.EventRepository;
import com.ticketmonster.deprecated_logic_for_reference.repositories.EventTicketRepository;
import com.ticketmonster.deprecated_logic_for_reference.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventTicketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTicketRepository eventTicketRepository;

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket()/*(@RequestBody Integer event_id, @RequestBody String seat, @RequestBody Integer numberOfTickets) */{

        int event_id = 2;
        int numberOfTickets = 2;
        String seat = "UPPER FLOOR";
        Event event = eventRepository.getOne(event_id);

        if(numberOfTickets <= event.getAvailable_tickets()){
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //User user = userRepository.findByEmail(authentication.getPrincipal().toString());
            User user = userRepository.findByEmail("admin@dummy.com");

            List<EventTicket> tickets = new ArrayList<EventTicket>();

            for (int i = 0; i < numberOfTickets; i++){
                EventTicket eventTicket = new EventTicket();
                eventTicket.setUser(user.getUser_id());
                eventTicket.setEvent_id(event.getEvent_id());
                eventTicket.setPrice(event.getPrice());
                eventTicket.setSeat(seat);
                tickets.add(eventTicketRepository.save(eventTicket));
            }
            event.setAvailable_tickets(event.getAvailable_tickets() - numberOfTickets);
            eventRepository.save(event);

            return new ResponseEntity<List<EventTicket>>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new CustomException("Sorry but it seems we don't have that many tickets available."), HttpStatus.RESET_CONTENT);
        }
    }

//    @PostMapping("/cancelTicket")
//    public List<EventTicket> cancelTicket(@PathVariable Long event_id){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Event event1 = eventRepository.getOne(event_id);
//
//        List<EventTicket> tickets = new ArrayList<EventTicket>();
//
//        EventTicket eventTicket = new EventTicket();
//        eventTicket.setUser(authentication.getName());
//        eventTicket.setEvent_id(event1.getEvent_id());
//        eventTicket.setPrice(event1.getPrice());
//        tickets.add(eventTicketRepository.save(eventTicket));
//
//        return tickets;
//    }

//    @GetMapping(path = "myTickets/{event_id}/printTicket.pdf", produces = "application/pdf")
//    public ResponseEntity<?> printTicket() {
//        PDDocument pdf = new PDDocument();
//
//        pdf.addPage(new PDPage());
//        PDStream pdStream = new PDStream(pdf);
//
//        try {
//            COSInputStream cosInputStream = pdStream.createInputStream();
//
//            return ResponseEntity.ok().contentLength(pdStream.getLength())
//                    .contentType(MediaType.parseMediaType("application/octet-stream"))
//                    .body(new InputStreamResource(cosInputStream));
//        } catch (IOException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
