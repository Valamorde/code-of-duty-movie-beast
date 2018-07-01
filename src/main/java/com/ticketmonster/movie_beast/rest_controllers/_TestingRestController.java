package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to easily test Business logic through REST.
 * If you need to test something try it here first and then move it to the right file.
 */
@RestController("/testing")
public class _TestingRestController {

    @Autowired
    ICityRepository cityRepository;

    @GetMapping("/test/city/{tcid}")
    public ResponseEntity<?> testCityTheatre(@PathVariable(value = "tcid") Integer id) {
        return new ResponseEntity<>(cityRepository.findByCityId(id).getTheatres(), HttpStatus.OK);
    }
}
