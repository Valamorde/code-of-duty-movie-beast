package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services.implementations.SeatReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class SeatReservationController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private SeatReservationServiceImpl seatReservationService;

    @PostMapping(value = "/seatReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reserveSeat(@RequestBody SeatReservation seatReservation) {
        try {
            return seatReservationService.reserveTicket(seatReservation.getShowId(), seatReservation.getSeatId(),
                    SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/cancelReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@RequestBody SeatReservation reservation) {
        try {
            return seatReservationService.cancelReservation(reservation.getSeatId(), SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
