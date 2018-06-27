package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
public class SeatReservationInitialization {

    @Autowired
    ISeatReservationRepository seatRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0,10).forEach((j -> {
            IntStream.range(0, 10).forEach((i -> {
                SeatReservation seat = new SeatReservation();
                seat.setTheatreId(j+1);
                seat.setShowId(j+1);
                seat.setSeatReserved(false);
                seat.setSeatPaid(false);
                seatRepository.save(seat);
            }));
        }));
    }
}
