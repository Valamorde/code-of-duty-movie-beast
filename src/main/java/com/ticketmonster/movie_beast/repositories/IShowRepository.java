package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IShowRepository extends JpaRepository<Show, Integer> {

    Show findByShowId(Integer showId);
}
