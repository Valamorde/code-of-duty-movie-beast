package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.repositories.TheatreRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class TheatreInitialization {

    List<String> theatreNames = new ArrayList<String>(Arrays.asList("COSMOS 1", "COSMOS 2", "COSMOS 3", "COSMOS 4", "COSMOS 5",
            "GALAXY 1","GALAXY 2","GALAXY 3","GALAXY 4","GALAXY 5",
            "CINEPLUS 1","CINEPLUS 2","CINEPLUS 3","CINEPLUS 4","CINEPLUS 5",
            "MOVIEDOM 1","MOVIEDOM 2","MOVIEDOM 3","MOVIEDOM 4","MOVIEDOM 5"));
    List<String> theatreAddresses = new ArrayList<String>(Arrays.asList("STREET, 1, TOWN 1", "STREET, 2, TOWN 2", "STREET, 3, TOWN 3", "STREET, 4, TOWN 4", "STREET, 5, TOWN 5",
            "STREET, 1, TOWN 6","STREET, 1, TOWN 7","STREET, 1, TOWN 8","STREET, 1, TOWN 9","STREET, 1, TOWN 10",
            "STREET, 1, TOWN 11","STREET, 1, TOWN 12","STREET, 1, TOWN 13","STREET, 1, TOWN 14","STREET, 1, TOWN 15",
            "STREET, 1, TOWN 16","STREET, 1, TOWN 17","STREET, 1, TOWN 18","STREET, 1, TOWN 19","STREET, 1, TOWN 20"));

    @Autowired
    TheatreRepository theatreRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 19).forEach((i -> {
            Theatre theatre = new Theatre();
            theatre.setTheatre_name(theatreNames.get(i));
            theatre.setTheatre_address(theatreAddresses.get(i));
            theatre.setCity_id(i+1);
            theatre.setMovie_id((i+1 < 5)?i+1:4);
            theatre.setMovie_release_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            theatreRepository.save(theatre);
        }));
    }
}
