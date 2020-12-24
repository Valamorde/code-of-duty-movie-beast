package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface MovieService {

    ResponseEntity<?> getAllMovies();

    ResponseEntity<?> getSingleMovie(Movie movie);

    ResponseEntity<?> getShowByMovie(Movie movie);

    ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication);

    ResponseEntity<?> generateNewMovie(Movie newMovie, Authentication authentication);

    ResponseEntity<?> updateSingleMovie(Movie movie, Movie movieDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleMovie(Movie movie, Authentication authentication);
}
