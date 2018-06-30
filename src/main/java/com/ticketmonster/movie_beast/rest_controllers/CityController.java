package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.helpers._deprecated_custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController
public class CityController {

    @Autowired
    ICityRepository cityRepository;

    // Get All Cities
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Get a Single City
    @GetMapping("/cities/{cId}")
    public City getCityById(@PathVariable(value = "cId") Integer cId) {
        return cityRepository.findById(cId).orElseThrow(() -> new ResourceNotFoundException("City", "Id", cId));
    }

    // Update a City
    @PutMapping("/cities/{id}")
    public City updateCity(@PathVariable(value = "id") Integer id, @Valid @RequestBody City cityDetails) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "Id", id));
        city.setCityName(cityDetails.getCityName());

        return cityRepository.save(city);
    }

    // Delete a City
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable(value = "id") Integer id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "Id", id));

        cityRepository.delete(city);

        return ResponseEntity.ok().build();
    }
}
