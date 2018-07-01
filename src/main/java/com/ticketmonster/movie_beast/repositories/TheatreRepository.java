package com.ticketmonster.movie_beast.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketmonster.movie_beast.models.Theatre;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {


    List<Theatre> findAllByMovieId(Integer movieId);
    List<Theatre> findAllByCityId(Integer cityId);
    Theatre findByTheatreId(Integer TheatreId);
}
