package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByMovieNameLike(String movieName);
}
