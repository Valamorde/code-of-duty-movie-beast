package com.ticketmonster.movie_beast.rest_controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ticketmonster.movie_beast.helpers.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services.implementations.SeatReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;

@Component
@RestController
public class SeatReservationController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private SeatReservationServiceImpl seatReservationService;

    @PostMapping("/seatReservation")
    @Consumes("application/json")
    public ResponseEntity<?> reserveSeat(@RequestBody ObjectNode bookingNode) {
        try {
            Integer showId = bookingNode.get("showId").asInt();
            Integer seat = bookingNode.get("seat").asInt();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(authentication.getName());

            return new ResponseEntity<>(seatReservationService.reserveTicket(showId, seat, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomException("Sorry but it seems that the seat you requested is unavailable."),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancelReservation")
    @Consumes("application/json")
    public ResponseEntity<?> cancelReservation(@RequestBody ObjectNode seatReservationNode) {
        try {
            Integer seatId = seatReservationNode.get("seatId").asInt();
            seatReservationService.cancelReservation(seatId);
            return new ResponseEntity<>("Your reservation has been successfully canceled.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomException("Sorry but something went wrong while cancelling your reservation."), HttpStatus.BAD_REQUEST);
        }
    }
}
