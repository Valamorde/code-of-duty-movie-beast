package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.ISeatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatReservationServiceImpl implements ISeatReservationService {

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> reserveTicket(SeatReservation seatReservation, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

//        Integer seatIndex = 0;        //FIXME: <-find common ground with frontend, decide to keep or delete
//        boolean isSeatAvailable = false;        //FIXME: <-find common ground with frontend, decide to keep or delete

        List<SeatReservation> showSeats = seatReservationRepository.findAllByShow(seatReservation.getShow());

//        for (int i = 0; i < showSeats.size(); i++) {        //FIXME: <-find common ground with frontend, decide to keep or delete
//            if (showSeats.get(seat).getBookingId() == null) {        //FIXME: <-find common ground with frontend, decide to keep or delete
//                isSeatAvailable = true;        //FIXME: <-find common ground with frontend, decide to keep or delete
//                seatIndex = showSeats.get(seat).getSeatId();        //FIXME: <-find common ground with frontend, decide to keep or delete
//            }        //FIXME: <-find common ground with frontend, decide to keep or delete
//        }        //FIXME: <-find common ground with frontend, decide to keep or delete

        SeatReservation bookedSeat = seatReservationRepository.getOne(seatReservation.getSeatId());

        if (seatReservation.getShow().getAvailableSeats() >= 1 && !bookedSeat.isSeatReserved()) {
            bookedSeat.setUser(user);
            bookedSeat.setSeatReserved(true);
            seatReservation.getShow().setAvailableSeats(seatReservation.getShow().getAvailableSeats() - 1);

            showRepository.save(seatReservation.getShow());
            seatReservationRepository.save(bookedSeat);
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

        return new ResponseEntity<>(seatReservationRepository.save(seatReservation), HttpStatus.OK);
    }
}
