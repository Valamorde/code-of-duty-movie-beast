package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.Movie;
import com.ticketmonster.moviebeast.rest.middleware.MovieMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MovieController extends AbstractController {

    @Autowired
    private MovieMediator movieMediator;

    @GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMovies() {
        try {
            return movieMediator.getAllMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovieById(@PathVariable(value = "movieId") Long movieId) {
        try {
            return movieMediator.getSingleMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/movies/{movieId}/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowByMovie(@PathVariable(value = "movieId") Long movieId) {
        try {
            return movieMediator.getShowByMovie(movieId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/admin/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewMovie(@Valid @RequestBody Movie newMovie) {
        try {
            return movieMediator.createNewMovie(newMovie, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/admin/movies/generateMovie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> generateNewMovie(@Valid @RequestBody Movie newMovie) {
        try {
            return movieMediator.generateNewMovie(newMovie, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/admin/movies/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMovie(@PathVariable(value = "movieId") Long movieId, @Valid @RequestBody Movie movieDetails) {
        try {
            return movieMediator.updateSingleMovie(movieId, movieDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/admin/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable(value = "movieId") Long movieId) {
        try {
            return movieMediator.deleteSingleMovie(movieId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
