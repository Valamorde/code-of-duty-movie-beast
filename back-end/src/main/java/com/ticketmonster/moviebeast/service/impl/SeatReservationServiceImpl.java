package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import com.ticketmonster.moviebeast.repository.ShowRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.SeatReservationService;
import com.ticketmonster.moviebeast.util.DatabaseCleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("seatReservationService")
public class SeatReservationServiceImpl implements SeatReservationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseCleanup cleanup;

    @Override
    public ResponseEntity<?> getAllSeats() {
        return new ResponseEntity<>(seatReservationRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSingleSeat(Long seatId) {
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

        SeatReservation bookedSeat = seatReservationRepository.getOne(seatReservation.getId());

        if (seatReservation.getShow().getAvailableSeats() >= 1 && !bookedSeat.isReserved()) {
            bookedSeat.setUser(user);
            bookedSeat.setReserved(true);
            seatReservation.getShow().setAvailableSeats(seatReservation.getShow().getAvailableSeats() - 1);

            showRepository.save(seatReservation.getShow());
            seatReservationRepository.save(bookedSeat);
            cleanup.cleanupTimer(user, bookedSeat);
            logger.info("Seat ID:[" + bookedSeat.getId() + "] reserved (5 min) for User ID:[" + user.getId() + "].");
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
        seatReservation.setPaid(false);
        seatReservation.setReserved(false);

        logger.info("Seat ID:[" + seatReservation.getId() + "] no longer reserved for User ID:[" + user.getId() + "].");
        return new ResponseEntity<>(seatReservationRepository.save(seatReservation), HttpStatus.OK);
    }
}
