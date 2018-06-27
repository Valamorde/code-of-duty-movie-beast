package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITheatreRepository extends JpaRepository<Theatre, Integer> {


    List<Theatre> findAllByMovieId(Integer movieId);
    List<Theatre> findAllByCityId(Integer cityId);
}
