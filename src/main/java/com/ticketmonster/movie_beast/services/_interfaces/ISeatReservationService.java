package com.ticketmonster.movie_beast.services._interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ISeatReservationService {

    ResponseEntity<?> reserveTicket(Integer showId, Integer seat, Authentication authentication);

    ResponseEntity<?> cancelReservation(Integer seatId, Authentication authentication);
}
