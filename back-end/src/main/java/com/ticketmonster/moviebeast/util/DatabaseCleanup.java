package com.ticketmonster.moviebeast.util;

import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class DatabaseCleanup {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeatReservationRepository seatReservationRepository;

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
        List<SeatReservation> seatReservations = seatReservationRepository.findAllByReservedIsTrueAndPaidIsFalseAndUserIs(user);

        logger.info("Initiating Cleanup for User with ID:[" + user.getId() + "] and  Seat Reservation with ID:[" + seatReservation.getId() + "]." + new Date());

        if (!seatReservation.isPaid()) {
            seatReservation.setReserved(false);
            seatReservation.setUser(null);
            seatReservationRepository.save(seatReservation);
            logger.info("Seat Reservation with ID:[" + seatReservation.getId() + "]. Cleanup Completed for User with ID:[" + user.getId() + "]... " + new Date());
        } else {
            logger.info("Seat Reservation with ID:[" + seatReservation.getId() + "] already paid for User with ID:[" + user.getId() + "]. No changes... " + new Date());
        }
    }
}
