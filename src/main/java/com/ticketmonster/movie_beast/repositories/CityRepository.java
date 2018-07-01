package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    City findByCityName(String cityName);
    City findByCityId(Integer cityId);
}
