package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IBookingService {

    ResponseEntity<?> bookAllInBasket(Authentication authentication);

    ResponseEntity<?> cancelSingleTicket(Integer bookingId);

    ResponseEntity<?> getAllBookings(Authentication authentication);

    ResponseEntity<?> getSingleBooking(Authentication authentication, Integer bookingId);

    ResponseEntity<?> updateSingleBooking(Authentication authentication, Integer bookingId, Booking newBooking);

    ResponseEntity<?> deleteSingleBooking(Authentication authentication, Integer bookingId);
}
