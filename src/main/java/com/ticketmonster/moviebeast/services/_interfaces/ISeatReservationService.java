package com.ticketmonster.moviebeast.services._interfaces;

import com.ticketmonster.moviebeast.models.SeatReservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ISeatReservationService {

    ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication);

    ResponseEntity<?> cancelReservation(SeatReservation seatReservation, Authentication authentication);
}
