package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BookingService {

    void bookAllInBasket(Authentication authentication);

    void cancelSingleTicket(Authentication authentication, Booking booking);

    List<Booking> getAllBookings(Authentication authentication);

    Booking getSingleBooking(Authentication authentication, Booking booking);

    void updateSingleBooking(Authentication authentication, Booking booking, Booking newBooking);

    void deleteSingleBooking(Authentication authentication, Booking booking);

    void printTickets(User user, HttpServletResponse res, HttpServletRequest req);

    void printTicketReport(User user, HttpServletResponse res, HttpServletRequest req);
}
