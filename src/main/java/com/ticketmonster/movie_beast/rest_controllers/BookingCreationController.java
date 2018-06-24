package com.ticketmonster.movie_beast.rest_controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.Seat;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RestController
public class BookingCreationController {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private ISeatRepository seatRepository;

    @PostMapping("/bookTicket")
    @Consumes("application/json")
    public ResponseEntity<?> bookTicket(@RequestBody ObjectNode bookingObjNode) {

        Integer show_id = bookingObjNode.get("show_id").asInt();
        Integer seat = bookingObjNode.get("seat").asInt();
        Integer seatIndex = 0;
        boolean isSeatAvailable = false;

        Show show = showRepository.getOne(show_id);

        List<Seat> showSeats = seatRepository.findAllByShowId(show.getShow_id());

        for (int i = 0; i < showSeats.size(); i++) {
            System.out.println(showSeats.get(i).getSeat_id().toString());
            if (showSeats.get(seat).getBooking_id() == null) {
                isSeatAvailable = true;
                seatIndex = showSeats.get(seat).getSeat_id();
            }
        }

        if (show.getAvailable_seats() >= 1 && isSeatAvailable) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(authentication.getName());

            List<Booking> bookings = new ArrayList<Booking>();

            Booking booking = new Booking();
            booking.setShow_id(show.getShow_id());
            booking.setUser_id(user.getUser_id());
            booking.setBooking_cost(show.getShow_cost());
            booking.setSeat_id(seatIndex);
            booking.setBooking_date(show.getShow_date());
            bookings.add(bookingRepository.save(booking));

            Optional<Seat> optionalBookedSeat = seatRepository.findById(seatIndex);
            Seat bookedSeat = optionalBookedSeat.get();
            bookedSeat.setSeat_reserved(true);
            bookedSeat.setBooking_id(bookings.get(0).getBooking_id());
            show.setAvailable_seats(show.getAvailable_seats() - 1);

            showRepository.save(show);
            seatRepository.save(bookedSeat);

            return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
        } else if (!isSeatAvailable) {
            return new ResponseEntity<>(
                    new CustomException("Sorry but it seems that the seat you requested got suddenly unavailable."), HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(
                    new CustomException("Sorry but it seems we don't have any seats left."), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // TODO: figure out how to print reserved ticket
    @GetMapping(path = "/shows/{show_id}/printedTicket.pdf", produces = "application/pdf")
    public ResponseEntity<?> printTicket(@PathVariable Integer show_id) {
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
