package com.ticketmonster.movie_beast.helpers;

import com.ticketmonster.movie_beast.models.*;
import com.ticketmonster.movie_beast.repositories.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Component
public class DatabaseInit {

    @Autowired
    ICityRepository cityRepository;
    @Autowired
    ITheatreRepository theatreRepository;
    @Autowired
    IMovieRepository movieRepository;
    @Autowired
    IShowRepository showRepository;
    @Autowired
    ISeatReservationRepository seatReservationRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private List<String> cities = new ArrayList<String>(Arrays.asList("Athina", "Piraias", "Peristeri", "Kallithea", "Nikaia"));
    private List<String> theatreNames = new ArrayList<String>(Arrays.asList("COSMOS 1", "COSMOS 2", "COSMOS 3", "COSMOS 4", "COSMOS 5"));
    private List<String> theatreAddresses = new ArrayList<String>(Arrays.asList("STREET, 1, TOWN 1", "STREET, 2, TOWN 2", "STREET, 3, TOWN 3", "STREET, 4, TOWN 4", "STREET, 5, TOWN 5"));
    private List<String> movieNames = new ArrayList<String>(Arrays.asList("The Commuter", "The Death of Stalin", "Black Panther", "The Rider", "Annihilation"));
    private List<String> movieDescriptions = new ArrayList<String>(Arrays.asList("Michael MacCauley, a former police officer goes through the same routine train commute to work and back every day only to be fired from his job as a life insurance salesman after ten years.",
            "Pianist Maria Yudina hides a note in a recording for Joseph Stalin, saying he has ruined the country. As Stalin reads it in his dacha, he is paralysed by a cerebral haemorrhage. The members of the Central Committee are alerted.",
            "Five African tribes war over a meteorite containing vibranium. One warrior ingests a \"heart-shaped herb\" affected by the metal and gains superhuman abilities, becoming the first \"Black Panther\" and forms the nation of Wakanda.",
            "Brady lives in financial poverty with his father Tim and his younger sister Lilly who suffers from brain damage from an accident at a bullriding competition. Doctors have told him he can no longer ride, or else his seizures will get worse.",
            "At the U.S. government's Area X facility on a southern coast, cellular biology professor and former U.S. Army soldier Lena undergoes a quarantined debriefing about an expedition into an anomaly called \"the shimmer\"."));

    @PostConstruct
    @Transactional
    public void init() {

        for (int i = 0; i < 5; i++) {
            City city = new City();
            city.setCityName(cities.get(i));
            cityRepository.save(city);
        }

        for (int i = 0; i < 5; i++) {
            Theatre theatre = new Theatre();
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            theatre.setTheatreName(theatreNames.get(i));
            theatre.setTheatreAddress(theatreAddresses.get(i));
            theatre.setCityId(i + 1);
            theatreRepository.save(theatre);
        }

        for (int i = 0; i < 5; i++) {
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            Movie movie = new Movie();
            movie.setMovieName(movieNames.get(i));
            movie.setMovieDescription(movieDescriptions.get(i));
            movie.setMovieReleaseDate(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            movie.setMovieDurationInMinutes(90);
            movie.setTheatreId(random);
            movieRepository.save(movie);
        }

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            Show show = new Show();
            show.setShowCost(new BigDecimal(3.5, MathContext.DECIMAL64));
            show.setMovieId(random);
            show.setShowDate(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            show.setAvailableSeats(10);
            show.setInitialSeats(10);
            showRepository.save(show);
            for (int j = 0; j < 5; j++) {
                SeatReservation seat = new SeatReservation();
                seat.setShowId(i + 1);
                seat.setSeatReserved(false);
                seat.setSeatPaid(false);
                seatReservationRepository.save(seat);
            }
        }

        User user = new User();
        user.setEmail("user@dummy.com");
        user.setFullName("User Userson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.ROLE_USER.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(DateUtils.round(DateUtils.addDays(new Date(), 0), Calendar.DATE));
        userRepository.save(user);

        user = new User();
        user.setEmail("admin@dummy.com");
        user.setFullName("Admin Adminson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(DateUtils.round(DateUtils.addDays(new Date(), 0), Calendar.DATE));
        userRepository.save(user);
    }
}
