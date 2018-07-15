package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.DatabaseCleanup;
import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import com.ticketmonster.moviebeast.repositories.IShowRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.ISeatReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The SeatReservationService class is responsible for handling the actions regarding the tickets,
 * meaning reserving a seat and canceling a reservation.
 */
@Service
public class SeatReservationServiceImpl implements ISeatReservationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private DatabaseCleanup cleanup;

    @Override
    public ResponseEntity<?> getAllSeats() {
        return new ResponseEntity<>(seatReservationRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSingleSeat(Integer seatId) {
        return new ResponseEntity<>(seatReservationRepository.getOne(seatId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<SeatReservation> showSeats = seatReservationRepository.findAllByShow(seatReservation.getShow());

        SeatReservation bookedSeat = seatReservationRepository.getOne(seatReservation.getSeatId());

        if (seatReservation.getShow().getAvailableSeats() >= 1 && !bookedSeat.isSeatReserved()) {
            bookedSeat.setUser(user);
            bookedSeat.setSeatReserved(true);
            seatReservation.getShow().setAvailableSeats(seatReservation.getShow().getAvailableSeats() - 1);

            showRepository.save(seatReservation.getShow());
            seatReservationRepository.save(bookedSeat);
            cleanup.cleanupTimer(user, bookedSeat);
            logger.info("Seat ID:[" + bookedSeat.getSeatId() + "] reserved (5 min) for User ID:[" + user.getUserId() + "].");
            return new ResponseEntity<>(bookedSeat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelReservation(SeatReservation seatReservation, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        seatReservation.setUser(null);
        seatReservation.setBooking(null);
        seatReservation.setSeatPaid(false);
        seatReservation.setSeatReserved(false);

        logger.info("Seat ID:[" + seatReservation.getSeatId() + "] no longer reserved for User ID:[" + user.getUserId() + "].");
        return new ResponseEntity<>(seatReservationRepository.save(seatReservation), HttpStatus.OK);
    }
}
