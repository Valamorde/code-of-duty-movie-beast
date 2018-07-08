package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.Movie;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IMovieRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Autowired
    private IMovieRepository movieRepository;

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
            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
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
            return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
