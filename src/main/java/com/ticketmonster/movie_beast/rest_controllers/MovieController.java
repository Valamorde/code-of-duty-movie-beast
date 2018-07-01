package com.ticketmonster.movie_beast.rest_controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmonster.movie_beast.models.Movie;
import com.ticketmonster.movie_beast.services.MovieService;

@Component
@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;

	// Get All Movies
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		List<Movie> list = movieService.getAllMovies();
		return new ResponseEntity<List<Movie>>(list, HttpStatus.OK);
	}

	//
	// Get a Single Movie
	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(value = "id") Integer id) {
		try {
			Movie movie = movieService.getMovieById(id);
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Update a Movie
	@PutMapping("/movies/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable(value = "id") Integer id, @Valid @RequestBody Movie movieDetails) {
		try {
			Movie movie = movieService.getMovieById(id);
			movie.setMovieName(movie.getMovieName());
			movieService.updateMovie(movie);
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// Delete a Movie
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable(value = "id") Integer id) {
		movieService.deleteMovie(id);
		return ResponseEntity.ok().build();
	}
}
