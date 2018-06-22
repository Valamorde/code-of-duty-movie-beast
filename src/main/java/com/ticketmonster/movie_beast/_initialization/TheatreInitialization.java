package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.repositories.ITheatreRepository;
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
            "GALAXY 1","GALAXY 2","GALAXY 3","GALAXY 4","GALAXY 5"));
    List<String> theatreAddresses = new ArrayList<String>(Arrays.asList("STREET, 1, TOWN 1", "STREET, 2, TOWN 2", "STREET, 3, TOWN 3", "STREET, 4, TOWN 4", "STREET, 5, TOWN 5",
            "STREET, 1, TOWN 6","STREET, 1, TOWN 7","STREET, 1, TOWN 8","STREET, 1, TOWN 9","STREET, 1, TOWN 10"));

    @Autowired
    ITheatreRepository theatreRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 10).forEach((i -> {
            Theatre theatre = new Theatre();
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            theatre.setTheatre_name(theatreNames.get(i));
            theatre.setTheatre_address(theatreAddresses.get(i));
            theatre.setCity_id(i+1);
            theatre.setMovie_id(random);
            theatre.setMovie_release_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            theatreRepository.save(theatre);
        }));
    }
}
