package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.rest.middleware.SeatReservationMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SeatReservationController extends AbstractController {

    @Autowired
    private SeatReservationMediator seatReservationMediator;

    @GetMapping(value = "/seats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSeats() {
        return seatReservationMediator.getAllSeats();
    }

    @GetMapping(value = "/seats/{seatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleSeat(@PathVariable(value = "seatId") Long seatId) {
        return seatReservationMediator.getSingleSeat(seatId);
    }

    @PostMapping(value = "/seatReservation/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reserveSeat(@RequestBody SeatReservation seatReservation) {
        try {
            return seatReservationMediator.reserveTicket(seatReservation, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/seatReservation/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelReservation(@RequestBody SeatReservation reservation) {
        try {
            return seatReservationMediator.cancelReservation(reservation, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
