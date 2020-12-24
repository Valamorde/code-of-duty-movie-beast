package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.BookingRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class BookingMediator {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    public Booking getBooking(Authentication authentication, Long bookingId) {
        Booking booking = bookingRepository.getOne(bookingId);
        return bookingService.getSingleBooking(authentication, booking);
    }

    public void updateBooking(Authentication authentication, Long bookingId, Booking bookingDetails) {
        Booking booking = bookingRepository.getOne(bookingId);
        bookingService.updateSingleBooking(authentication, booking, bookingDetails);
    }

    public void deleteBooking(Authentication authentication, Long bookingId) {
        Booking booking = bookingRepository.getOne(bookingId);
        bookingService.deleteSingleBooking(authentication, booking);
    }

    public List<Booking> getAll(Authentication authentication) {
        return bookingService.getAllBookings(authentication);
    }

    public void bookAllInBasket(Authentication authentication) {
        bookingService.bookAllInBasket(authentication);
    }

    public void cancelTicket(Authentication authentication, Booking booking) {
        bookingService.cancelSingleTicket(authentication, booking);
    }

    public void printTicketReport(Authentication authentication, HttpServletResponse res, HttpServletRequest req) {
        User user = userRepository.findByEmail(authentication.getName());
        bookingService.printTicketReport(user, res, req);
    }
}
