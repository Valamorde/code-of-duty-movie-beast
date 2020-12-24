package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
}
