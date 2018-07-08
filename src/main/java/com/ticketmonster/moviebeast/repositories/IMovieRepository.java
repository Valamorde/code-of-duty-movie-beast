package com.ticketmonster.moviebeast.repositories;

import com.ticketmonster.moviebeast.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByMovieNameLike(String movieName);
}
