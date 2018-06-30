package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services.implementations.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/bookings/final")
    @Produces("application/json")
    public ResponseEntity<?> finalizeBookings() {
        try {
            return bookingService.bookTickets(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Cancel a Booked Ticket
    @PostMapping("/bookings/cancel")
    @Consumes("application/json")
    public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking) {
        try {
            return bookingService.cancelSingleTicket(booking.getBookingId());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Bookings
    @GetMapping("/bookings")
    @Produces("application/json")
    public ResponseEntity<?> getAllBookings() {
        try {
            return bookingService.findAllBookings(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Booking
    @GetMapping("/bookings/{id}")
    @Produces("application/json")
    public ResponseEntity<?> getBookingById(@PathVariable(value = "id") Integer id) {
        try {
            return bookingService.findSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Booking
    @PutMapping("/bookings/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseEntity<?> updateBooking(@PathVariable(value = "id") Integer id, @Valid @RequestBody Booking bookingDetails) {
        try {
            return bookingService.updateSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id, bookingDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Allows Admin to delete a Booking
    @DeleteMapping("/bookings/{id}")
    @Produces("application/json")
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "id") Integer id) {
        try {
            return bookingService.deleteSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
