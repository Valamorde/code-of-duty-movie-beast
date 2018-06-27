package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShowRepository extends JpaRepository<Show, Integer> {

    List<Show> findAllByTheatreId(Integer theatreId);
    List<Show> findAllByMovieId(Integer movieId);
    List<Show> findAllByAvailableSeats(Integer availableSeats);
    Show findByShowId(Integer showId);
}
