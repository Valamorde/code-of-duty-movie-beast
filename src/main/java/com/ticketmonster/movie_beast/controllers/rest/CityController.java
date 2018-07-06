package com.ticketmonster.movie_beast.controllers.rest;

import com.ticketmonster.movie_beast.controllers.middleware.CityMediator;
import com.ticketmonster.movie_beast.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CityController {

    @Autowired
    private CityMediator cityMediator;

    // Get All Cities
    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCities() {
        try {
            return cityMediator.getAllCities();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single City
    @GetMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCityById(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityMediator.getSingleCity(cityId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New City
    @PostMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCity(@Valid @RequestBody City newCity) {
        try {
            return cityMediator.createNewCity(newCity, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a City
    @PutMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCity(@PathVariable(value = "cityId") Integer cityId, @Valid @RequestBody City cityDetails) {
        try {
            return cityMediator.updateSingleCity(cityId, cityDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a City
    @DeleteMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCity(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityMediator.deleteSingleCity(cityId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
