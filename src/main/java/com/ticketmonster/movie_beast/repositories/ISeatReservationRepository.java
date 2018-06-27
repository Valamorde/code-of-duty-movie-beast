package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeatReservationRepository extends JpaRepository<SeatReservation, Integer> {

    List<SeatReservation> findAllByTheatreId(Integer theatreId);
    List<SeatReservation> findAllByShowId(Integer showId);
    SeatReservation findByBookingId(Integer bookingId);
    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalse();
    List<SeatReservation> findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(Integer userId);
    SeatReservation findBySeatId(Integer seatId);
}
