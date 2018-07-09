package com.ticketmonster.moviebeast.controllers.middleware;

import com.ticketmonster.moviebeast.models.Movie;
import com.ticketmonster.moviebeast.repositories.IMovieRepository;
import com.ticketmonster.moviebeast.services.implementations.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The Movie Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.moviebeast.services.*.*Movie*
 *     ~>   com.ticketmonster.moviebeast.controllers.rest.Movie*
 */
@Component
public class MovieMediator {

    @Autowired
    private IMovieRepository movieRepository;
    @Autowired
    private MovieServiceImpl movieService;

    public ResponseEntity<?> getAllMovies() {
        return movieService.getAllMovies();
    }

    public ResponseEntity<?> getSingleMovie(Integer movieId) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.getSingleMovie(movie);
    }

    public ResponseEntity<?> getShowByMovie(Integer movieId){
        Movie movie = movieRepository.getOne(movieId);
        return movieService.getShowByMovie(movie);
    }

    public ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication) {
        return movieService.createNewMovie(newMovie, authentication);
    }

    public ResponseEntity<?> updateSingleMovie(Integer movieId, Movie movieDetails, Authentication authentication) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.updateSingleMovie(movie, movieDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleMovie(Integer movieId, Authentication authentication) {
        Movie movie = movieRepository.getOne(movieId);
        return movieService.deleteSingleMovie(movie, authentication);
    }
}
