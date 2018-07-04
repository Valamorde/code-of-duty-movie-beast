package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.services.implementations.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class CityController {

    @Autowired
    CityServiceImpl cityService;

    // Get All Cities
    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCities() {
        try {
            return cityService.getAllCities();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single City
    @GetMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCityById(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityService.getSingleCity(cityId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New City
    @PostMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCity(@Valid @RequestBody City newCity) {
        try {
            return cityService.createNewCity(newCity, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a City
    @PutMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCity(@PathVariable(value = "cityId") Integer cityId, @Valid @RequestBody City cityDetails) {
        try {
            return cityService.updateSingleCity(cityId, cityDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a City
    @DeleteMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCity(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityService.deleteSingleCity(cityId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
