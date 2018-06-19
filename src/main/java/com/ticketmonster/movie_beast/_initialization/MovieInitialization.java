package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Movie;
import com.ticketmonster.movie_beast.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class MovieInitialization {

    List<String> movieNames = new ArrayList<String>(Arrays.asList("The Commuter", "The Death of Stalin", "Black Panther", "The Rider", "Annihilation"));
    List<String> movieDescriptions = new ArrayList<String>(Arrays.asList("Michael MacCauley, a former police officer goes through the same routine train commute to work and back every day only to be fired from his job as a life insurance salesman after ten years.",
            "Pianist Maria Yudina hides a note in a recording for Joseph Stalin, saying he has ruined the country. As Stalin reads it in his dacha, he is paralysed by a cerebral haemorrhage. The members of the Central Committee are alerted.",
            "Five African tribes war over a meteorite containing vibranium. One warrior ingests a \"heart-shaped herb\" affected by the metal and gains superhuman abilities, becoming the first \"Black Panther\" and forms the nation of Wakanda.",
            "Brady lives in financial poverty with his father Tim and his younger sister Lilly who suffers from brain damage from an accident at a bullriding competition. Doctors have told him he can no longer ride, or else his seizures will get worse.",
            "At the U.S. government's Area X facility on a southern coast, cellular biology professor and former U.S. Army soldier Lena undergoes a quarantined debriefing about an expedition into an anomaly called \"the shimmer\"."));


    @Autowired
    MovieRepository movieRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 5).forEach((i -> {
            Movie movie = new Movie();
            movie.setMovie_name(movieNames.get(i));
            movie.setMovie_description(movieDescriptions.get(i));
            movieRepository.save(movie);
        }));
    }
}
