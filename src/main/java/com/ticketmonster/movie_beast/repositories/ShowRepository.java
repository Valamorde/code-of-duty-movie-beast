package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    @Query("SELECT s FROM Show s WHERE s.theatre_id = :theatre_id")
    List<Show> findByTheatreId(@Param("theatre_id") Integer theatre_id);

    @Query("SELECT s FROM Show s WHERE s.movie_id = :movie_id")
    List<Show> findByMovieId(@Param("movie_id") Integer movie_id);

    @Query("SELECT s FROM Show s WHERE s.available_seats >= :available_seats")
    List<Show> findByAvailableSeats(@Param("available_seats") Integer available_seats);
}
