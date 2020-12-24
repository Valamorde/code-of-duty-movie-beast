package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import com.ticketmonster.moviebeast.service.SeatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SeatReservationMediator {

    @Autowired
    private SeatReservationRepository seatReservationRepository;
    @Autowired
    private SeatReservationService seatReservationService;

    public ResponseEntity<?> getAllSeats() {
        return seatReservationService.getAllSeats();
    }

    public ResponseEntity<?> getSingleSeat(Long seatId) {
        return seatReservationService.getSingleSeat(seatId);
    }

    public ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication) {
        SeatReservation seat = seatReservationRepository.getOne(seatReservation.getId());
        return seatReservationService.reserveTicket(seat, authentication);
    }

    public ResponseEntity<?> cancelReservation(SeatReservation seatReservation, Authentication authentication) {
        SeatReservation seat = seatReservationRepository.getOne(seatReservation.getId());
        return seatReservationService.cancelReservation(seat, authentication);
    }
}
