package com.ticketmonster.moviebeast.helpers;

import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * DatabaseCleanup helper is responsible for checking for unpaid tickets and deleting them from the database, every 3 minutes.
 */
@Component
public class DatabaseCleanup {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Scheduled(fixedRate = 180000)
    @Transactional
    public void cleanupUnpaidReservations(){
        try {
            List<SeatReservation> seatReservations = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalse();

            logger.warn("Initiating Database Cleanup... " + new Date());

            for (int i = 0; i < seatReservations.size(); i++) {
                seatReservations.get(i).setSeatReserved(false);
                seatReservations.get(i).setUser(null);
                seatReservationRepository.save(seatReservations.get(i));
            }

            logger.warn("Database Cleanup Complete... " + new Date());
        }catch (Exception e){
            logger.error("Database Cleanup failed... ", e);
        }
    }
}
