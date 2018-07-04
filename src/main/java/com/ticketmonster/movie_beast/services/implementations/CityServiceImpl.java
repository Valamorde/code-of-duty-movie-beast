package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.ICityRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    ICityRepository cityRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Override
    public ResponseEntity<?> getAllCities() {
        return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleCity(Integer cityId) {
        return new ResponseEntity<>(cityRepository.getOne(cityId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewCity(City cityDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            City city = new City();
            city.setCityName(cityDetails.getCityName());
            city.setTheatres(cityDetails.getTheatres());
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleCity(Integer cityId, City cityDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            City city = cityRepository.getOne(cityId);
            city.setCityName(cityDetails.getCityName());
            city.setTheatres(cityDetails.getTheatres());
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleCity(Integer cityId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            cityRepository.delete(cityRepository.getOne(cityId));
            return new ResponseEntity<>(cityRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
