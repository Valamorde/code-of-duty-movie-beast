package com.ticketmonster.movie_beast.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.repositories.CityRepository;
import com.ticketmonster.movie_beast.services.CityService;

@Service
public class CityServiceImpl implements CityService {

	
	@Autowired
    CityRepository cityRepository;
	 
	@Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

	@Override
    public City getCityById(Integer id) {
        return cityRepository.findByCityId(id);
    }

   
	@Override
    public City updateCity(City city) {
        return cityRepository.save(city);
    }

	  
	@Override
    public void deleteCity(Integer id) {
        City city = cityRepository.findByCityId(id);
        cityRepository.delete(city);
      
    }
}
