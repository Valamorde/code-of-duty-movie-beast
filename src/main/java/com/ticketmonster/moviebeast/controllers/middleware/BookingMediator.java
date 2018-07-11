package com.ticketmonster.moviebeast.controllers.middleware;

import com.ticketmonster.moviebeast.models.Booking;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IBookingRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services.implementations.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Booking Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.moviebeast.services.*.*Booking*
 * ~>   com.ticketmonster.moviebeast.controllers.rest.Booking*
 */
@Component
public class BookingMediator {

    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private IUserRepository userRepository;

    public ResponseEntity<?> getBooking(Authentication authentication, Integer bookingId) {
        Booking booking = bookingRepository.getOne(bookingId);
        return bookingService.getSingleBooking(authentication, booking);
    }

    public ResponseEntity<?> updateBooking(Authentication authentication, Integer bookingId, Booking bookingDetails) {
        Booking booking = bookingRepository.getOne(bookingId);
        return bookingService.updateSingleBooking(authentication, booking, bookingDetails);
    }

    public ResponseEntity<?> deleteBooking(Authentication authentication, Integer bookingId) {
        Booking booking = bookingRepository.getOne(bookingId);
        return bookingService.deleteSingleBooking(authentication, booking);
    }

    public ResponseEntity<?> getAll(Authentication authentication) {
        return bookingService.getAllBookings(authentication);
    }

    public ResponseEntity<?> bookAllInBasket(Authentication authentication) {
        return bookingService.bookAllInBasket(authentication);
    }

    public ResponseEntity<?> cancelTicket(Authentication authentication, Booking booking) {
        return bookingService.cancelSingleTicket(authentication, booking);
    }

    public void printTicketReport(Authentication authentication, HttpServletResponse res, HttpServletRequest req) {
        User user = userRepository.findByEmail(authentication.getName());
        bookingService.printTicketReport(user, res, req);
    }
}
