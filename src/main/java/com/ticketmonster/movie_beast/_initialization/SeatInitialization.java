package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Seat;
import com.ticketmonster.movie_beast.repositories.ISeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
public class SeatInitialization {

    @Autowired
    ISeatRepository seatRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0,10).forEach((j -> {
            IntStream.range(0, 10).forEach((i -> {
                Seat seat = new Seat();
                seat.setTheatre_id(j+1);
                seat.setShow_id(j+1);
                seat.setSeat_reserved(false);
                seat.setPaid(false);
                seatRepository.save(seat);
            }));
        }));
    }
}
