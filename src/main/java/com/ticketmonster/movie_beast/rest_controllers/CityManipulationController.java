package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.custom_exceptions.ResourceNotFoundException;
import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController(value = "/city")
public class CityManipulationController {

    @Autowired
    CityRepository cityRepository;

    // Get All Cities
    @GetMapping("/all")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Get a Single City
    @GetMapping("/{id}")
    public City getCityById(@PathVariable(value = "id") Integer id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "Id", id));
    }

    // Update a City
    @PutMapping("/{id}")
    public City updateCity(@PathVariable(value = "id") Integer id, @Valid @RequestBody City cityDetails) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "Id", id));
        city.setCity_name(cityDetails.getCity_name());

        City updatedUser = cityRepository.save(city);
        return updatedUser;
    }

    // Delete a City
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable(value = "id") Integer id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City", "Id", id));

        cityRepository.delete(city);

        return ResponseEntity.ok().build();
    }
}
