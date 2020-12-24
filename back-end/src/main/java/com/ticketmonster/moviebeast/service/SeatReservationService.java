package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.SeatReservation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface SeatReservationService {

    ResponseEntity<?> getAllSeats();

    ResponseEntity<?> getSingleSeat(Long seatId);

    ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication);

    ResponseEntity<?> cancelReservation(SeatReservation seatReservation, Authentication authentication);
}
