package com.ticketmonster.moviebeast.repositories;

import com.ticketmonster.moviebeast.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShowRepository extends JpaRepository<Show, Integer> {
}
