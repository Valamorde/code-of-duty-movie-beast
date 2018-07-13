package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.City;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.ICityRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This CityServiceImpl is responsible for the actions regarding the cities.
 * Namely, fetches info when a user asks for all or a single city, while allows the admin to create, update and delete a city.
 */
@Service
public class CityServiceImpl implements ICityService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    public ResponseEntity<?> getAllCities() {
        return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleCity(City city) {
        return new ResponseEntity<>(cityRepository.getOne(city.getCityId()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewCity(City newCity, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            City city = new City();
            city.setCityName(newCity.getCityName());
            city.setTheatres(newCity.getTheatres());
            logger.info("Created New City with Name:[" + city.getCityName() + "].");
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleCity(City city, City cityDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            city.setCityName(cityDetails.getCityName());
            city.setTheatres(cityDetails.getTheatres());
            logger.info("Updated City with ID:[" + city.getCityId() + "].");
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleCity(City city, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            cityRepository.delete(city);
            logger.info("Deleted City with ID:[" + city.getCityId() + "].");
            return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
