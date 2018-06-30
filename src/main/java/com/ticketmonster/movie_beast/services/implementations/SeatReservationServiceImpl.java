package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.Show;
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
    IShowRepository showRepository;

    @Autowired
    ISeatReservationRepository seatReservationRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> reserveTicket(Integer showId, Integer seat, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

//        Integer seatIndex = 0;        //FIXME: <-find common ground with frontend, decide to keep or delete
//        boolean isSeatAvailable = false;        //FIXME: <-find common ground with frontend, decide to keep or delete

        Show show = showRepository.findByShowId(showId);

        List<SeatReservation> showSeats = seatReservationRepository.findAllByShowId(show.getShowId());

//        for (int i = 0; i < showSeats.size(); i++) {        //FIXME: <-find common ground with frontend, decide to keep or delete
//            if (showSeats.get(seat).getBookingId() == null) {        //FIXME: <-find common ground with frontend, decide to keep or delete
//                isSeatAvailable = true;        //FIXME: <-find common ground with frontend, decide to keep or delete
//                seatIndex = showSeats.get(seat).getSeatId();        //FIXME: <-find common ground with frontend, decide to keep or delete
//            }        //FIXME: <-find common ground with frontend, decide to keep or delete
//        }        //FIXME: <-find common ground with frontend, decide to keep or delete

        SeatReservation bookedSeat = seatReservationRepository.getOne(seat);

        if (show.getAvailableSeats() >= 1 && !bookedSeat.isSeatReserved()) {
            bookedSeat.setUserId(user.getUserId());
            bookedSeat.setSeatReserved(true);
            show.setAvailableSeats(show.getAvailableSeats() - 1);

            showRepository.save(show);
            seatReservationRepository.save(bookedSeat);
        }
        return new ResponseEntity<>(bookedSeat, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelReservation(Integer seatId, Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SeatReservation seatReservation = seatReservationRepository.findBySeatId(seatId);
        seatReservation.setUserId(null);
        seatReservation.setBookingId(null);
        seatReservation.setSeatPaid(false);
        seatReservation.setSeatReserved(false);

        return new ResponseEntity<>(seatReservationRepository.save(seatReservation), HttpStatus.OK);
    }
}
