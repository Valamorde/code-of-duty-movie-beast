package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeatReservationRepository extends JpaRepository<SeatReservation, Integer> {

    List<SeatReservation> findAllByShow(Show show);

    SeatReservation findByBooking(Booking booking);

    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalse();

    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIs(User user);

    List<SeatReservation> findAllByUser(User user);
}
