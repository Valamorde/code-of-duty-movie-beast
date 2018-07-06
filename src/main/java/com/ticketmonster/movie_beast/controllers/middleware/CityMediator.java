package com.ticketmonster.movie_beast.controllers.middleware;

import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.repositories.ICityRepository;
import com.ticketmonster.movie_beast.services.implementations.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The City Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.movie_beast.services.*.*City*
 *     ~>   com.ticketmonster.movie_beast.controllers.rest.City*
 */
@Component
public class CityMediator {

    @Autowired
    private CityServiceImpl cityService;
    @Autowired
    private ICityRepository cityRepository;

    public ResponseEntity<?> getAllCities() {
        return cityService.getAllCities();
    }

    public ResponseEntity<?> getSingleCity(Integer cityId) {
        City city = cityRepository.getOne(cityId);
        return cityService.getSingleCity(city);
    }

    public ResponseEntity<?> createNewCity(City newCity, Authentication authentication) {
        return cityService.createNewCity(newCity, authentication);
    }

    public ResponseEntity<?> updateSingleCity(Integer cityId, City cityDetails, Authentication authentication) {
        City city = cityRepository.getOne(cityId);
        return cityService.updateSingleCity(city, cityDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleCity(Integer cityId, Authentication authentication) {
        City city = cityRepository.getOne(cityId);
        return cityService.deleteSingleCity(city, authentication);
    }
}
