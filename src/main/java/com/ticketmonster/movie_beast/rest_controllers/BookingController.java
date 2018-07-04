package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services.implementations.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class BookingController {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BookingServiceImpl bookingService;

    // Book Reserved Seats
    @PostMapping(value = "/bookings/final", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> finalizeBookings() {
        try {
            return bookingService.bookAllInBasket(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Cancel a Booked Ticket
    @PostMapping(value = "/bookings/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking) {
        try {
            return bookingService.cancelSingleTicket(booking.getBookingId());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Bookings
    @GetMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings() {
        try {
            return bookingService.getAllBookings(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Booking
    @GetMapping(value = "/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookingById(@PathVariable(value = "id") Integer id) {
        try {
            return bookingService.getSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Booking
    @PutMapping(value = "/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBooking(@PathVariable(value = "id") Integer id, @Valid @RequestBody Booking bookingDetails) {
        try {
            return bookingService.updateSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id, bookingDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Allows Admin to delete a Booking
    @DeleteMapping(value = "/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "id") Integer id) {
        try {
            return bookingService.deleteSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
