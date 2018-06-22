package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITheatreRepository extends JpaRepository<Theatre, Integer> {

    @Query("SELECT t FROM Theatre t WHERE t.movie_id = :movie_id")
    List<Theatre> findByMovieId(@Param("movie_id") Integer movie_id);

    @Query("SELECT t FROM Theatre t WHERE t.city_id = :city_id")
    List<Theatre> findByCityId(@Param("city_id") Integer city_id);
}
