package com.ticketmonster.moviebeast.helpers;

import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * DatabaseCleanup helper is responsible for checking for unpaid tickets and deleting them from the database, every 3 minutes.
 */
@Component
public class DatabaseCleanup {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    public void cleanupTimer(User user, SeatReservation seatReservation) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    cleanupUnpaidReservations(user, seatReservation);
                }
            }, 300000);

        } catch (Exception e) {
            logger.error("Database Cleanup failed... ", e);
        }
    }

    public void cleanupUnpaidReservations(User user, SeatReservation seatReservation) {
        List<SeatReservation> seatReservations = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIs(user);
        
        logger.info("Initiating Cleanup for User with ID:[" + user.getUserId() + "] and  Seat Reservation with ID:[" + seatReservation.getSeatId() + "]." + new Date());

        if (!seatReservation.isSeatPaid()) {
            seatReservation.setSeatReserved(false);
            seatReservation.setUser(null);
            seatReservationRepository.save(seatReservation);
        }

        logger.info("Seat Reservation with ID:[" + seatReservation.getSeatId() + "]. Cleanup Completed for User with ID:[" + user.getUserId() + "]... " + new Date());
    }
}
