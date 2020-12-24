package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByNameLike(String cityName);
}
