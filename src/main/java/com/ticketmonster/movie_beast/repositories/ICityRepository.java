package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer> {

    City findByCityNameLike(String cityName);
}
