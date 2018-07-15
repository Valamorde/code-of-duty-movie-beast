package com.ticketmonster.moviebeast.repositories;

import com.ticketmonster.moviebeast.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer> {

    City findByCityNameLike(String cityName);
}
