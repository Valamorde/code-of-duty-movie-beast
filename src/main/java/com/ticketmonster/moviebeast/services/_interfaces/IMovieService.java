package com.ticketmonster.moviebeast.services._interfaces;

import com.ticketmonster.moviebeast.models.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IMovieService {

    ResponseEntity<?> getAllMovies();

    ResponseEntity<?> getSingleMovie(Movie movie);

    ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication);

    ResponseEntity<?> updateSingleMovie(Movie movie, Movie movieDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleMovie(Movie movieId, Authentication authentication);
}
