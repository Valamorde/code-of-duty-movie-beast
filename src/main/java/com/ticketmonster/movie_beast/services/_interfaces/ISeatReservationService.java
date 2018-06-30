package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ISeatReservationService {

    ResponseEntity<?> reserveTicket(Integer showId, Integer seat, Authentication authentication);
    ResponseEntity<?> cancelReservation(Integer seatId, Authentication authentication);
}
