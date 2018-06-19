package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.movie_name LIKE :movie_name")
    List<Movie> findAllByName(@Param("movie_name") String movie_name);
}
