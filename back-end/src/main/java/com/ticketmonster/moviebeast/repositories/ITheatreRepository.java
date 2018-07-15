package com.ticketmonster.moviebeast.repositories;

import com.ticketmonster.moviebeast.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheatreRepository extends JpaRepository<Theatre, Integer> {

}
