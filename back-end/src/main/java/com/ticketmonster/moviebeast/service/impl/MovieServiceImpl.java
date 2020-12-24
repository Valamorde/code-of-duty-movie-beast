package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.Movie;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.Show;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.MovieRepository;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import com.ticketmonster.moviebeast.repository.ShowRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.MovieService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
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

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Override
    @Transactional
    public ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleMovie(Movie movie) {
        return new ResponseEntity<>(movieRepository.getOne(movie.getId()), HttpStatus.OK);
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
            movie.setName(newMovie.getName());
            movie.setDescription(newMovie.getDescription());
            movie.setReleaseDate(newMovie.getReleaseDate());
            movie.setShows(newMovie.getShows());
            movie.setTheatre(newMovie.getTheatre());
            movie.setMinutes(newMovie.getMinutes());
            movie.setTrailerURL(newMovie.getTrailerURL());
            logger.info("Created New Movie with Name:[" + movie.getName() + "] auto-generated 3 Shows with 5 Seats each.");

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
            movie.setName(newMovie.getName());
            movie.setDescription(newMovie.getDescription());
            movie.setReleaseDate(newMovie.getReleaseDate());
            movie.setShows(newMovie.getShows());
            movie.setTheatre(newMovie.getTheatre());
            movie.setMinutes(newMovie.getMinutes());
            movie.setTrailerURL(newMovie.getTrailerURL());
            movieRepository.save(movie);
            for (int j = 0; j < 3; j++) {
                Show show = new Show();
                show.setCost(new BigDecimal(3.5, MathContext.DECIMAL64));
                show.setMovie(movie);
                show.setDate(DateUtils.round(DateUtils.addDays(movie.getReleaseDate(), j + 1 / 2), Calendar.DATE));
                show.setAvailableSeats(5);
                show.setInitialSeats(5);
                showRepository.save(show);
                for (int k = 0; k < show.getInitialSeats(); k++) {
                    SeatReservation seat = new SeatReservation();
                    seat.setShow(showRepository.getOne(show.getId()));
                    seat.setPaid(false);
                    seat.setReserved(false);
                    seatReservationRepository.save(seat);
                }
            }
            logger.info("Generated New Movie with Name:[" + movie.getName() + "] auto-generated 3 Shows with 5 Seats each.");

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
            movie.setName(movieDetails.getName());
            movie.setDescription(movieDetails.getDescription());
            movie.setReleaseDate(movieDetails.getReleaseDate());
            movie.setShows(movieDetails.getShows());
            movie.setTheatre(movieDetails.getTheatre());
            movie.setMinutes(movieDetails.getMinutes());
            movie.setTrailerURL(movieDetails.getTrailerURL());
            logger.info("Updated Movie with ID:[" + movie.getId() + "].");
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
            logger.info("Deleted Movie with ID:[" + movie.getId() + "].");
            return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
