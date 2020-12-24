package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

}
