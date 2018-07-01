package com.ticketmonster.movie_beast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmonster.movie_beast.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByMovieNameLike(String movieName);
    Movie findBymovieId(Integer movieId);
}
