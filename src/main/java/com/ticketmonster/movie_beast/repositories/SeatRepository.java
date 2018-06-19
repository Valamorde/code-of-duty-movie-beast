package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.theatre_id = :theatre_id")
    List<Seat> findByTheatreId(@Param("theatre_id") Integer theatre_id);

    @Query("SELECT s FROM Seat s WHERE s.show_id = :show_id")
    List<Seat> findByShowId(@Param("show_id") Integer show_id);

    @Query("SELECT s FROM Seat s WHERE s.booking_id = :booking_id")
    List<Seat> findByBookingId(@Param("booking_id") Integer booking_id);
}
