package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.ISeatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SeatReservation reserveTicket(Integer showId, Integer seat, User user) {

        Integer seatIndex = 0;
        boolean isSeatAvailable = false;

        Show show = showRepository.findByShowId(showId);

        List<SeatReservation> showSeats = seatReservationRepository.findAllByShowId(show.getShowId());

        for (int i = 0; i < showSeats.size(); i++) {
            if (showSeats.get(seat).getBookingId() == null) {
                isSeatAvailable = true;
                seatIndex = showSeats.get(seat).getSeatId();
            }
        }

        SeatReservation bookedSeat = seatReservationRepository.getOne(seatIndex);

        if (show.getAvailableSeats() >= 1 && isSeatAvailable) {
            bookedSeat.setUserId(user.getUserId());
            bookedSeat.setSeatReserved(true);
            show.setAvailableSeats(show.getAvailableSeats() - 1);

            showRepository.save(show);
            seatReservationRepository.save(bookedSeat);
        }
        return bookedSeat;
    }

    @Override
    @Transactional
    public void cancelReservation(Integer seatId){
        SeatReservation seatReservation = seatReservationRepository.findBySeatId(seatId);

        seatReservation.setUserId(null);
        seatReservation.setBookingId(null);
        seatReservation.setSeatPaid(false);
        seatReservation.setSeatReserved(false);
        seatReservationRepository.save(seatReservation);
    }
}
