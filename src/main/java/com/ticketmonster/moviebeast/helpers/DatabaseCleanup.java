package com.ticketmonster.moviebeast.helpers;

import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DatabaseCleanup helper is responsible for checking for unpaid tickets and deleting them from the database, every 3 minutes.
 */
@Component
public class DatabaseCleanup {

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Scheduled(fixedRate = 180000)
    public void cleanupUnpaidReservations(){
        List<SeatReservation> seatReservations = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalse();

        for(int i=0; i< seatReservations.size(); i++){
            seatReservations.get(i).setSeatReserved(false);
            seatReservations.get(i).setUser(null);
            seatReservationRepository.save(seatReservations.get(i));
        }
    }
}
