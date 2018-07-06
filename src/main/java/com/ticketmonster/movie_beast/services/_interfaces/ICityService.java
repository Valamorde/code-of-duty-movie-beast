package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.City;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ICityService {

    ResponseEntity<?> getAllCities();

    ResponseEntity<?> getSingleCity(City city);

    ResponseEntity<?> createNewCity(City newCity, Authentication authentication);

    ResponseEntity<?> updateSingleCity(City city, City cityDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleCity(City city, Authentication authentication);
}
