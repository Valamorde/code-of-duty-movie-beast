package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.CityMediator;
import com.ticketmonster.moviebeast.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This Rest Controller is responsible for the Cities category.
 * It allows the user to view all or a single city and the admin to create, update or delete a specific or all cities.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CityController {

    @Autowired
    private CityMediator cityMediator;

    //<editor-fold desc="User Operations">
    /**
     * Allows the user to view all cities
     *
     * @return list of all cities
     */
    @GetMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCities() {
        try {
            return cityMediator.getAllCities();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single city
     *
     * @param cityId
     * @return specific city
     */
    @GetMapping(value = "/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCityById(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityMediator.getSingleCity(cityId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Admin Operations">
    /**
     * Allows the admin to create a new city
     *
     * @param newCity
     * @return saves new city (status ok), or status error
     */
    @PostMapping(value = "/admin/cities", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewCity(@Valid @RequestBody City newCity) {
        try {
            return cityMediator.createNewCity(newCity, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to update a city
     *
     * @param cityId
     * @param cityDetails
     * @return saves city's updates (status ok), or status error
     */
    @PutMapping(value = "/admin/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCity(@PathVariable(value = "cityId") Integer cityId, @Valid @RequestBody City cityDetails) {
        try {
            return cityMediator.updateSingleCity(cityId, cityDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to delete a city
     *
     * @param cityId
     * @return current list of cities, or status error
     */
    @DeleteMapping(value = "/admin/cities/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCity(@PathVariable(value = "cityId") Integer cityId) {
        try {
            return cityMediator.deleteSingleCity(cityId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>
}
