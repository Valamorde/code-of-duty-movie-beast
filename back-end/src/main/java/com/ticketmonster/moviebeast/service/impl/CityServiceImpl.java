package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.City;
import com.ticketmonster.moviebeast.repository.CityRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.CityService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cityService")
public class CityServiceImpl implements CityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    @Transactional
    public City getSingleCity(Long cityId) {
        return cityRepository.getOne(cityId);
    }

    @Override
    @Transactional
    public void createNewCity(City newCity, Authentication authentication) {
        City city = new City();
        city.setName(newCity.getName());
        city.setTheatres(newCity.getTheatres());
        cityRepository.save(city);
        logger.info("Created New City with Name:[" + city.getName() + "].");
    }

    @Override
    @Transactional
    public void updateSingleCity(City city, City cityDetails, Authentication authentication) {
        city.setName(cityDetails.getName());
        city.setTheatres(cityDetails.getTheatres());
        cityRepository.save(city);
        logger.info("Updated City with ID:[" + city.getId() + "].");
    }

    @Override
    @Transactional
    public void deleteSingleCity(City city, Authentication authentication) {
        cityRepository.delete(city);
        logger.info("Deleted City with ID:[" + city.getId() + "].");
        cityRepository.findAll();
    }
}
