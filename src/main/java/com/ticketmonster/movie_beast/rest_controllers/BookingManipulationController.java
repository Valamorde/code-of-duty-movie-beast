package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.BookingRepository;
import com.ticketmonster.movie_beast.repositories.ShowRepository;
import com.ticketmonster.movie_beast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController("/bookings")
public class BookingManipulationController {

    @Autowired
    BookingRepository bookingRepository;

    // Get All Bookings
    @GetMapping("/bookings/all")
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
        booking.setUser_id(bookingDetails.getUser_id());
        booking.setBooking_date(bookingDetails.getBooking_date());
        booking.setShow_id(bookingDetails.getShow_id());

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
