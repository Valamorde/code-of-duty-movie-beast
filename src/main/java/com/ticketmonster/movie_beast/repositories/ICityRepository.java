package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Integer> {

    @Query("SELECT c FROM City c WHERE c.city_name = :city_name")
    City findByName(@Param("city_name")String city_name);
}
