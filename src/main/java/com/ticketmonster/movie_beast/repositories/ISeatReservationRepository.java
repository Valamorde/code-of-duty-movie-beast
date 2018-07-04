package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeatReservationRepository extends JpaRepository<SeatReservation, Integer> {

    List<SeatReservation> findAllByShowId(Integer showId);

    SeatReservation findByBooking(Booking booking);

    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalse();

    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(Integer userId);

    List<SeatReservation> findAllByUserId(Integer userId);
}
