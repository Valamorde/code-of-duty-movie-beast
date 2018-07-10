package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.SeatReservationMediator;
import com.ticketmonster.moviebeast.models.SeatReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * This Rest Controller is responsible for the Seat Reservation.
 * It allows the user to make and cancel a seat reservation, after they have logged in.
 * In case of error, throws a 400 - Bad Request message.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class SeatReservationController {

    @Autowired
    private SeatReservationMediator seatReservationMediator;

    /**
     * Allows the user to get a list of all seats
     *
     * @return
     */
    @GetMapping(value = "/seats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSeats(){
        return seatReservationMediator.getAllSeats();
    }

    /**
     * Allows the user to get a specific seat
     */
    @GetMapping(value = "/seats/{seatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSingleSeat(@PathVariable(value = "seatId") Integer seatId){
        return seatReservationMediator.getSingleSeat(seatId);
    }

    /**
     * Allows user to make a seat reservation
     *
     * @param seatReservation
     * @return if user is not registered-> status error, if registered: books seat, or status error
     */
    @PostMapping(value = "/seatReservations/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> reserveSeat(@RequestBody SeatReservation seatReservation) {
        try {
            return seatReservationMediator.reserveTicket(seatReservation, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to cancel a seat reservation
     *
     * @param reservation
     * @return if user is not registered-> status error, if registered: saves changes, or status error
     */
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
