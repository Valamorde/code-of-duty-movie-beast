package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheatreRepository extends JpaRepository<Theatre, Integer> {

}
