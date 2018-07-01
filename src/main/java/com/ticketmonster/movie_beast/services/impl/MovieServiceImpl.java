package com.ticketmonster.movie_beast.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ticketmonster.movie_beast.models.Movie;
import com.ticketmonster.movie_beast.repositories.MovieRepository;
import com.ticketmonster.movie_beast.services.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
    MovieRepository movieRepository;

	@Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

	@Override
    public Movie getMovieById(@PathVariable(value = "id") Integer id) {
        return movieRepository.findBymovieId(id);
    }

	@Override
    public Movie updateMovie(Movie movie) {
		return movieRepository.save(movie);
    }

	@Override
	public void deleteMovie(Integer id) {
		Movie movie = movieRepository.findBymovieId(id);
		movieRepository.delete(movie);

	}
}
