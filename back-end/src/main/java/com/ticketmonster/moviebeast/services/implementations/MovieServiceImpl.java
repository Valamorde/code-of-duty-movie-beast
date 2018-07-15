package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.Movie;
import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.models.Show;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IMovieRepository;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import com.ticketmonster.moviebeast.repositories.IShowRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.IMovieService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;

/**
 * The class MovieServiceImpl handles the actions regarding movies.
 * Namely, it enables users to fetch info for all or a signle movie, and the admin to create, update and delete a movie.
 */
@Service
public class MovieServiceImpl implements IMovieService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Override
    @Transactional
    public ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleMovie(Movie movie) {
        return new ResponseEntity<>(movieRepository.getOne(movie.getMovieId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getShowByMovie(Movie movie) {
        return new ResponseEntity<>(movie.getShows(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Movie movie = new Movie();
            movie.setMovieName(newMovie.getMovieName());
            movie.setMovieDescription(newMovie.getMovieDescription());
            movie.setMovieReleaseDate(newMovie.getMovieReleaseDate());
            movie.setShows(newMovie.getShows());
            movie.setTheatre(newMovie.getTheatre());
            movie.setMovieDurationInMinutes(newMovie.getMovieDurationInMinutes());
            movie.setTrailerURL(newMovie.getTrailerURL());
            logger.info("Created New Movie with Name:[" + movie.getMovieName() + "] auto-generated 3 Shows with 5 Seats each.");

            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> generateNewMovie(Movie newMovie, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Movie movie = new Movie();
            movie.setMovieName(newMovie.getMovieName());
            movie.setMovieDescription(newMovie.getMovieDescription());
            movie.setMovieReleaseDate(newMovie.getMovieReleaseDate());
            movie.setShows(newMovie.getShows());
            movie.setTheatre(newMovie.getTheatre());
            movie.setMovieDurationInMinutes(newMovie.getMovieDurationInMinutes());
            movie.setTrailerURL(newMovie.getTrailerURL());
            movieRepository.save(movie);
            for (int j = 0; j < 3; j++) {
                Show show = new Show();
                show.setShowCost(new BigDecimal(3.5, MathContext.DECIMAL64));
                show.setMovie(movie);
                show.setShowDate(DateUtils.round(DateUtils.addDays(movie.getMovieReleaseDate(), j + 1 / 2), Calendar.DATE));
                show.setAvailableSeats(5);
                show.setInitialSeats(5);
                showRepository.save(show);
                for (int k = 0; k < show.getInitialSeats(); k++) {
                    SeatReservation seat = new SeatReservation();
                    seat.setShow(showRepository.getOne(show.getShowId()));
                    seat.setSeatPaid(false);
                    seat.setSeatReserved(false);
                    seatReservationRepository.save(seat);
                }
            }
            logger.info("Generated New Movie with Name:[" + movie.getMovieName() + "] auto-generated 3 Shows with 5 Seats each.");

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleMovie(Movie movie, Movie movieDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            movie.setMovieName(movieDetails.getMovieName());
            movie.setMovieDescription(movieDetails.getMovieDescription());
            movie.setMovieReleaseDate(movieDetails.getMovieReleaseDate());
            movie.setShows(movieDetails.getShows());
            movie.setTheatre(movieDetails.getTheatre());
            movie.setMovieDurationInMinutes(movieDetails.getMovieDurationInMinutes());
            movie.setTrailerURL(movieDetails.getTrailerURL());
            logger.info("Updated Movie with ID:[" + movie.getMovieId() + "].");
            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleMovie(Movie movie, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            movieRepository.delete(movie);
            logger.info("Deleted Movie with ID:[" + movie.getMovieId() + "].");
            return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
