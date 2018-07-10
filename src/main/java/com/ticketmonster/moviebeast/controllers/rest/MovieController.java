package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.MovieMediator;
import com.ticketmonster.moviebeast.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This Rest Controller is responsible for the Movies category.
 * It allows the user to view all or a single movie and the admin to create, update or delete a specific or all movies.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController {

    @Autowired
    private MovieMediator movieMediator;

    //<editor-fold desc="User Operations">
    /**
     * Allows the user to view all movies
     *
     * @return list with all movies
     */
    @GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMovies() {
        try {
            return movieMediator.getAllMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single movie
     *
     * @param movieId
     * @return specific movie
     */
    @GetMapping(value = "/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieById(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return movieMediator.getSingleMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single movie's shows
     *
     * @param movieId
     * @return a list of the specified movie's shows
     */
    @GetMapping(value = "/movies/{movieId}/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowByMovie(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return movieMediator.getShowByMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Admin Operations">
    /**
     * Allows the admin to create a new movie
     *
     * @param newMovie
     * @return
     */
    @PostMapping(value = "/admin/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewMovie(@Valid @RequestBody Movie newMovie) {
        try {
            return movieMediator.createNewMovie(newMovie, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to update a movie
     *
     * @param movieId
     * @param movieDetails
     * @return saves new movie (status ok), or status error
     */
    @PutMapping(value = "/admin/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMovie(@PathVariable(value = "movieId") Integer movieId, @Valid @RequestBody Movie movieDetails) {
        try {
            return movieMediator.updateSingleMovie(movieId, movieDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to delete a movie
     *
     * @param movieId
     * @return current list of movies, or status error
     */
    @DeleteMapping("/admin/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return movieMediator.deleteSingleMovie(movieId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>
}
