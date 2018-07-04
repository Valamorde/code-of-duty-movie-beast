package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.Movie;
import com.ticketmonster.movie_beast.services._interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class MovieController {

    @Autowired
    IMovieService movieService;

    // Get All Movies
    @GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMovies() {
        try {
            return movieService.getAllMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Movie
    @GetMapping(value = "/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieById(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return movieService.getSingleMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New Movie
    @PostMapping("/movies")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseEntity<?> createNewMovie(@Valid @RequestBody Movie newMovie) {
        try {
            return movieService.createNewMovie(newMovie, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Movie
    @PutMapping("/movies/{movieId}")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseEntity<?> updateMovie(@PathVariable(value = "movieId") Integer movieId, @Valid @RequestBody Movie movieDetails) {
        try {
            return movieService.updateSingleMovie(movieId, movieDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a Movie
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return movieService.deleteSingleMovie(movieId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
