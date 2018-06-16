package com.ticketmonster.ticketbeast.controllers;

import com.ticketmonster.ticketbeast.models.Event;
import com.ticketmonster.ticketbeast.models.EventTicket;
import com.ticketmonster.ticketbeast.repositories.EventRepository;
import com.ticketmonster.ticketbeast.repositories.EventTicketRepository;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EventTicketController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTicketRepository eventTicketRepository;

    @PostMapping("/bookTicket")
    public List<EventTicket> bookTicket(@PathVariable Long event_id, @PathVariable String seat) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Event event1 = eventRepository.getOne(event_id);

        List<EventTicket> tickets = new ArrayList<EventTicket>();

        EventTicket eventTicket = new EventTicket();
        eventTicket.setUser(authentication.getName());
        eventTicket.setEvent_id(event1.getEvent_id());
        eventTicket.setPrice(event1.getPrice());
        eventTicket.setSeat(seat);
        tickets.add(eventTicketRepository.save(eventTicket));

        return tickets;
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

    @GetMapping(path = "/printTicket.pdf", produces = "application/pdf")
    public ResponseEntity<?> printTicket() {
        PDDocument pdf = new PDDocument();

        pdf.addPage(new PDPage());
        PDStream pdStream = new PDStream(pdf);

        try {
            COSInputStream cosInputStream = pdStream.createInputStream();

            return ResponseEntity.ok().contentLength(pdStream.getLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(cosInputStream));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
