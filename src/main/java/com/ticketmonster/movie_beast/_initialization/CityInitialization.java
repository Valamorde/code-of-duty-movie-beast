package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.City;
import com.ticketmonster.movie_beast.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class CityInitialization {

    List<String> cities = new ArrayList<String>(Arrays.asList("Athina", "Piraias", "Peristeri", "Kallithea", "Nikaia", "Keratsini",
            "Ilion", "Gliphada", "Zographos", "Ilioupoli"));

    @Autowired
    ICityRepository cityRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 10).forEach((i -> {
            City city = new City();
            city.setCityName(cities.get(i));
            cityRepository.save(city);
        }));
    }
}
