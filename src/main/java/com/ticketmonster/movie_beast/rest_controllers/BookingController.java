package com.ticketmonster.movie_beast.rest_controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services.implementations.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import java.util.List;

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
    public ResponseEntity<?> finalizeBookings() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(authentication.getName());

            return new ResponseEntity<>(bookingService.bookTicket(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomException("Sorry but it seems that the seat you requested is unavailable."),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Cancel a Booked Ticket
    @PostMapping("/bookings/cancel")
    @Consumes("application/json")
    public ResponseEntity<?> cancelBooking(@RequestBody ObjectNode bookingNode) {
        try {
            Integer bookingId = bookingNode.get("bookingId").asInt();

            return new ResponseEntity<>(bookingService.cancelTicket(bookingId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomException("Sorry but it seems that the booking could not be cancelled."),
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Bookings
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get a Single Booking
    @GetMapping("/bookings/{id}")
    public Booking getBookingById(@PathVariable(value = "id") Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));
    }

    // Update a Booking
    @PutMapping("/bookings/{id}")
    public Booking updateBooking(@PathVariable(value = "id") Integer id, @Valid @RequestBody Booking bookingDetails) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));
        booking.setUserId(bookingDetails.getUserId());
        booking.setBookingDate(bookingDetails.getBookingDate());
        booking.setShowId(bookingDetails.getShowId());

        Booking updatedBooking = bookingRepository.save(booking);
        return updatedBooking;
    }

    // Delete a Booking
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "id") Integer id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));

        bookingRepository.delete(booking);

        return ResponseEntity.ok().build();
    }
}
