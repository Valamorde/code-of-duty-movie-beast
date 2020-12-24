package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.Show;
import com.ticketmonster.moviebeast.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, Long> {

    List<SeatReservation> findAllByShow(Show show);

    SeatReservation findByBooking(Booking booking);

    List<SeatReservation> findAllByReservedIsTrueAndPaidIsFalse();

    List<SeatReservation> findAllByReservedIsTrueAndPaidIsFalseAndUserIs(User user);

    List<SeatReservation> findAllByUser(User user);
}
