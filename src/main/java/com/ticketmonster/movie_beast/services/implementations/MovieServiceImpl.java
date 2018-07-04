package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Movie;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IMovieRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Autowired
    IMovieRepository movieRepository;

    @Override
    @Transactional
    public ResponseEntity<?> getAllMovies() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleMovie(Integer movieId) {
        return new ResponseEntity<>(movieRepository.getOne(movieId), HttpStatus.OK);
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
            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleMovie(Integer movieId, Movie movieDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Movie movie = movieRepository.getOne(movieId);
            movie.setMovieName(movieDetails.getMovieName());
            movie.setMovieDescription(movieDetails.getMovieDescription());
            movie.setMovieReleaseDate(movieDetails.getMovieReleaseDate());
            movie.setShows(movieDetails.getShows());
            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleMovie(Integer movieId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            movieRepository.delete(movieRepository.getOne(movieId));
            return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
