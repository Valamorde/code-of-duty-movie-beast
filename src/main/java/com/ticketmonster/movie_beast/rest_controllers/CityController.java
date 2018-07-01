package com.ticketmonster.movie_beast.rest_controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.services.CityService;

@Component
@RestController
public class CityController {

	@Autowired
	private CityService CityService;
	

 // Get All Cities
    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
		List<City> list = CityService.getAllCities();
		return new ResponseEntity<List<City>>(list, HttpStatus.OK);
	}

    // Get a Single City
    @GetMapping("/cities/{cId}")
    public ResponseEntity<City> getCityById(@PathVariable("cId") Integer cId) {
    	try {
    		City city = CityService.getCityById(cId);
    	    return new ResponseEntity<City>(city, HttpStatus.OK);
    	 } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
	}

    
 // Update a City
    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity(@PathVariable(value = "id") Integer id, @Valid @RequestBody City cityDetails) {
    	try {
    		City city = CityService.getCityById(id);
    		city.setCityName(cityDetails.getCityName());
    		CityService.updateCity(city);
    		 return new ResponseEntity<City>(city, HttpStatus.OK);
    	 } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
    	
    }

    // Delete a City
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable(value = "id") Integer id) {
        CityService.deleteCity(id);
        return ResponseEntity.ok().build();
   }
}
