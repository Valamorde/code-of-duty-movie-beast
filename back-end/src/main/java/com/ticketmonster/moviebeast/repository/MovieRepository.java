package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAllByNameLike(String movieName);
}
