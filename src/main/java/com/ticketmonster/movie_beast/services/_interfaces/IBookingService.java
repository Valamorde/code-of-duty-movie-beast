package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.User;

import java.util.List;

public interface IBookingService {

    List<Booking> bookTicket(User user);
    Booking cancelTicket(Integer bookingId);
}
