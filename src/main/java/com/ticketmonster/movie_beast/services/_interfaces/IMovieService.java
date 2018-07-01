package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IMovieService {

    ResponseEntity<?> getAllMovies();

    ResponseEntity<?> getSingleMovie(Integer movieId);

    ResponseEntity<?> createNewMovie(Movie newMovie, Authentication authentication);

    ResponseEntity<?> updateSingleMovie(Integer movieId, Movie movieDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleMovie(Integer movieId, Authentication authentication);
}
