package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.City;
import com.ticketmonster.moviebeast.repository.CityRepository;
import com.ticketmonster.moviebeast.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityMediator {

    @Autowired
    private CityService cityService;
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    public City getSingleCity(Long cityId) {
        return cityService.getSingleCity(cityId);
    }

    public void createNewCity(City newCity, Authentication authentication) {
        cityService.createNewCity(newCity, authentication);
    }

    public void updateSingleCity(Long cityId, City cityDetails, Authentication authentication) {
        City city = cityRepository.getOne(cityId);
        cityService.updateSingleCity(city, cityDetails, authentication);
    }

    public void deleteSingleCity(Long cityId, Authentication authentication) {
        City city = cityRepository.getOne(cityId);
        cityService.deleteSingleCity(city, authentication);
    }
}
