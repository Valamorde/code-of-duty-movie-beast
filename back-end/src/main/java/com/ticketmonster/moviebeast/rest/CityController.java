package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.City;
import com.ticketmonster.moviebeast.rest.middleware.CityMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CityController extends AbstractController {

    @Autowired
    private CityMediator cityMediator;

    @GetMapping(
            value = "/cities",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<City> getCities() {
        return cityMediator.getAllCities();
    }

    @GetMapping(
            value = "/cities/{cityId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public City getCity(@PathVariable(value = "cityId") Long cityId) {
        return cityMediator.getSingleCity(cityId);
    }

    @PostMapping(
            value = "/admin/cities",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createNewCity(@RequestBody City newCity) {
        cityMediator.createNewCity(
                newCity,
                SecurityContextHolder.getContext().getAuthentication()
        );
    }

    @PutMapping(
            value = "/admin/cities/{cityId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCity(@PathVariable(value = "cityId") Long cityId,
                           @RequestBody City cityDetails) {
        cityMediator.updateSingleCity(
                cityId,
                cityDetails,
                SecurityContextHolder.getContext().getAuthentication()
        );
    }

    @DeleteMapping(
            value = "/admin/cities/{cityId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable(value = "cityId") Long cityId) {
        cityMediator.deleteSingleCity(
                cityId,
                SecurityContextHolder.getContext().getAuthentication()
        );
    }
}
