package com.ticketmonster.movie_beast.controllers.rest;

import com.ticketmonster.movie_beast.controllers.middleware.BookingMediator;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.services.implementations.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class BookingController {

    @Autowired
    private BookingMediator bookingMediator;

    // Book Reserved Seats
    @PostMapping(value = "/bookings/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkout() {
        try {
            return bookingMediator.bookAllInBasket(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Cancel a Booked Ticket
    @PostMapping(value = "/bookings/cancel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking) {
        try {
            return bookingMediator.cancelTicket(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Bookings
    @GetMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings() {
        try {
            return bookingMediator.getAll(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Booking
    @GetMapping(value = "/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookingById(@PathVariable(value = "bookingId") Integer bookingId) {
        try {
            return bookingMediator.getBooking(SecurityContextHolder.getContext().getAuthentication(), bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Booking
    @PutMapping(value = "/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBooking(@PathVariable(value = "bookingId") Integer bookingId, @Valid @RequestBody Booking bookingDetails) {
        try {
            return bookingMediator.updateBooking(SecurityContextHolder.getContext().getAuthentication(), bookingId, bookingDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Allows Admin to delete a Booking
    @DeleteMapping(value = "/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "bookingId") Integer bookingId) {
        try {
            return bookingMediator.deleteBooking(SecurityContextHolder.getContext().getAuthentication(),bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
