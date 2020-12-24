package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.Movie;
import com.ticketmonster.moviebeast.repository.MovieRepository;
import com.ticketmonster.moviebeast.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MovieMediator {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;

    public ResponseEntity<?> getAllMovies() {
        return movieService.getAllMovies();
    }

    public ResponseEntity<?> getSingleMovie(Long movieId) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.getSingleMovie(movie);
    }

    public ResponseEntity<?> getShowByMovie(Long movieId) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.getShowByMovie(movie);
    }

    public ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication) {
        return movieService.createNewMovie(newMovie, authentication);
    }

    public ResponseEntity<?> generateNewMovie(Movie newMovie, Authentication authentication) {
        return movieService.generateNewMovie(newMovie, authentication);
    }

    public ResponseEntity<?> updateSingleMovie(Long movieId, Movie movieDetails, Authentication authentication) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.updateSingleMovie(movie, movieDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleMovie(Long movieId, Authentication authentication) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.deleteSingleMovie(movie, authentication);
    }
}
