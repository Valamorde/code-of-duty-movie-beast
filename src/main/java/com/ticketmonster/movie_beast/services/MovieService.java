package com.ticketmonster.movie_beast.services;

import java.util.List;

import com.ticketmonster.movie_beast.models.Movie;

public interface MovieService {

	List<Movie> getAllMovies();
	Movie getMovieById(Integer id);
    Movie updateMovie(Movie movie);
	void deleteMovie(Integer id);
	
}
