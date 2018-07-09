package com.ticketmonster.moviebeast.helpers;

import com.ticketmonster.moviebeast.models.*;
import com.ticketmonster.moviebeast.repositories.*;
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

    private final List<String> cities = new ArrayList<String>(Arrays.asList(
            "Athina",
            "Piraias",
            "Peristeri",
            "Kallithea",
            "Nikaia"
    ));
    private final List<String> theatreNames = new ArrayList<String>(Arrays.asList(
            "Odeon Opera",
            "Cinema Votsalakia",
            "Cinema Cine City",
            "Odeon Starcity",
            "Cine Nikea"
    ));
    private final List<String> theatreAddresses = new ArrayList<String>(Arrays.asList(
            "Leof. Akadimias 57",
            "Leof. Vasileos Pavlou 4",
            "Leof. Konstantinoupoleos 82",
            "Leof. Sygrou 111",
            "Leof. Thivon 245"
    ));
    private final List<String> movieNames = new ArrayList<String>(Arrays.asList(
            "The Commuter",
            "The Death of Stalin",
            "Black Panther",
            "The Rider",
            "Annihilation",
            "Deadpool 2",
            "Solo: A Star Wars Story",
            "The Predator",
            "Ready Player One"
    ));
    private final List<String> movieDescriptions = new ArrayList<String>(Arrays.asList(
            "Michael MacCauley, a former police officer goes through the same routine train commute to work and back every day only to be fired from his job as a life insurance salesman after ten years.",
            "Pianist Maria Yudina hides a note in a recording for Joseph Stalin, saying he has ruined the country. As Stalin reads it in his dacha, he is paralysed by a cerebral haemorrhage. The members of the Central Committee are alerted.",
            "Five African tribes war over a meteorite containing vibranium. One warrior ingests a \"heart-shaped herb\" affected by the metal and gains superhuman abilities, becoming the first \"Black Panther\" and forms the nation of Wakanda.",
            "Brady lives in financial poverty with his father Tim and his younger sister Lilly who suffers from brain damage from an accident at a bullriding competition. Doctors have told him he can no longer ride, or else his seizures will get worse.",
            "At the U.S. government's Area X facility on a southern coast, cellular biology professor and former U.S. Army soldier Lena undergoes a quarantined debriefing about an expedition into an anomaly called \"the shimmer\".",
            "Wisecracking mercenary Deadpool meets Russell, an angry teenage mutant who lives at an orphanage. When Russell becomes the target of Cable Deadpool realizes that he'll need some help saving the boy from such a superior enemy.",
            "Young Han Solo finds adventure when he joins a gang of galactic smugglers. Indebted to the gangster Dryden Vos, the crew devises a daring plan to travel to the mining planet Kessel to steal a batch of valuable coaxium. ",
            "From the outer reaches of space to the small-town streets of suburbia, the hunt comes home. The universe's most lethal hunters are stronger, smarter and deadlier than ever before, having genetically upgraded themselves with DNA from other species.",
            "The film is set in 2045, with the world on the brink of chaos and collapse. But the people have found salvation in the OASIS, an expansive virtual reality universe created by the brilliant and eccentric James Halliday."
    ));
    private final List<String> trailerURLs = new ArrayList<>(Arrays.asList(
            "aDshY43Ol2U",
            "E9eAshaPvYw",
            "dxWvtMOGAhw",
            "AlrWRttLTkg",
            "89OP78l9oF0",
            "D86RtevtfrA",
            "jPEYpryMp2s",
            "WaG1KZqrLvM",
            "cSp1dM2Vj48"
    ));
    private final List<Integer> movieDurations = new ArrayList<>(Arrays.asList(
            105,
            107,
            135,
            105,
            120,
            120,
            135,
            107,
            139
    ));
    private final List<Date> movieReleaseDates = new ArrayList<>(Arrays.asList(
            new Date(1515369600000L),
            new Date(1540043824000L),
            new Date(1517184000000L),
            new Date(1523577600000L),
            new Date(1518480000000L),
            new Date(1526342400000L),
            new Date(1525910400000L),
            new Date(1536796800000L),
            new Date(1522281600000L)
    ));

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
            theatre.setTheatreName(theatreNames.get(i));
            theatre.setTheatreAddress(theatreAddresses.get(i));
            theatre.setCity(cityRepository.getOne(i + 1));
            theatreRepository.save(theatre);
        }

        for (int i = 0; i < 9; i++) {
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            Movie movie = new Movie();
            movie.setMovieName(movieNames.get(i));
            movie.setMovieDescription(movieDescriptions.get(i));
            movie.setMovieReleaseDate(movieReleaseDates.get(i));
            movie.setMovieDurationInMinutes(movieDurations.get(i));
            movie.setTheatre(theatreRepository.getOne(random));
            movie.setTrailerURL(trailerURLs.get(i));
            movieRepository.save(movie);
            for (int j = 0; j < 3; j++) {
                Show show = new Show();
                show.setShowCost(new BigDecimal(3.5, MathContext.DECIMAL64));
                show.setMovie(movie);
                show.setShowDate(DateUtils.round(DateUtils.addDays(movieReleaseDates.get(i), j + 1 / 2), Calendar.DATE));
                show.setAvailableSeats(5);
                show.setInitialSeats(5);
                showRepository.save(show);
                for (int k = 0; k < show.getInitialSeats(); k++) {
                    SeatReservation seat = new SeatReservation();
                    seat.setShow(showRepository.getOne(show.getShowId()));
                    seat.setSeatReserved(false);
                    seat.setSeatPaid(false);
                    seatReservationRepository.save(seat);
                }
            }
        }

        User user = new User();
        user.setEmail("user@dummy.com");
        user.setFullName("User Userson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.ROLE_USER.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("admin@dummy.com");
        user.setFullName("Admin Adminson");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("korvesism@gmail.com");
        user.setFullName("Markos Korvesis");
        user.setPassword(bCryptPasswordEncoder.encode("dev1"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("alampanoua@gmail.com");
        user.setFullName("Angeliki Alampanou");
        user.setPassword(bCryptPasswordEncoder.encode("dev2"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("nancy.at.nic@gmail.com");
        user.setFullName("Nancy Nicolaides");
        user.setPassword(bCryptPasswordEncoder.encode("dev3"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("rudolf.dalidis@gmail.com");
        user.setFullName("Rudolf Dalidis");
        user.setPassword(bCryptPasswordEncoder.encode("dev4"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("paulinamparniadaki@hotmail.com");
        user.setFullName("Paulina Barniadakis");
        user.setPassword(bCryptPasswordEncoder.encode("dev5"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);

        user = new User();
        user.setEmail("ioannis.smirnios@intrasoft-intl.com");
        user.setFullName("Ioannis Smirnios");
        user.setPassword(bCryptPasswordEncoder.encode("dev6"));
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        userRepository.save(user);
    }
}
