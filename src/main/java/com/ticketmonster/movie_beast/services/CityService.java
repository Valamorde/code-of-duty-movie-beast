package com.ticketmonster.movie_beast.services;

import java.util.List;

import com.ticketmonster.movie_beast.models.City;

public interface CityService {

	List<City> getAllCities();
	City getCityById(Integer id);
	City updateCity(City city);
	void deleteCity(Integer id);
}
