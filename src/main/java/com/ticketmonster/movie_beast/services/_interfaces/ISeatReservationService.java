package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;

import java.util.List;

public interface ISeatReservationService {

    SeatReservation reserveTicket(Integer showId, Integer seat, User user);
    void cancelReservation(Integer seatId);
}
