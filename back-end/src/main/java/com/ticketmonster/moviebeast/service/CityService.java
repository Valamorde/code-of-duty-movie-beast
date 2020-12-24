package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.City;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CityService {

    List<City> getAllCities();

    City getSingleCity(Long cityId);

    void createNewCity(City newCity, Authentication authentication);

    void updateSingleCity(City city, City cityDetails, Authentication authentication);

    void deleteSingleCity(City city, Authentication authentication);
}
