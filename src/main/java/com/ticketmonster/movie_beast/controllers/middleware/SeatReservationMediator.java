package com.ticketmonster.movie_beast.controllers.middleware;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.services.implementations.SeatReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The Seat Reservation Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.movie_beast.services.*.*SeatReservation*
 *     ~>   com.ticketmonster.movie_beast.controllers.rest.SeatReservation*
 */
@Component
public class SeatReservationMediator {

    @Autowired
    private ISeatReservationRepository seatReservationRepository;
    @Autowired
    private SeatReservationServiceImpl seatReservationService;

    public ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication){
        SeatReservation seat = seatReservationRepository.getOne(seatReservation.getSeatId());
        return seatReservationService.reserveTicket(seat, authentication);
    }

    public ResponseEntity<?> cancelReservation(SeatReservation seatReservation, Authentication authentication){
        SeatReservation seat = seatReservationRepository.getOne(seatReservation.getSeatId());
        return seatReservationService.cancelReservation(seat, authentication);
    }
}
