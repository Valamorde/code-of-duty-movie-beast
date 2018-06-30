package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.awt.print.Book;
import java.util.List;

public interface IBookingService {

    ResponseEntity<?> bookTickets(Authentication authentication);
    ResponseEntity<?> cancelSingleTicket(Integer bookingId);
    ResponseEntity<?> findAllBookings(Authentication authentication);
    ResponseEntity<?> findSingleBooking(Authentication authentication, Integer bookingId);
    ResponseEntity<?> updateSingleBooking(Authentication authentication, Integer bookingId, Booking newBooking);
    ResponseEntity<?> deleteSingleBooking(Authentication authentication, Integer bookingId);
}
