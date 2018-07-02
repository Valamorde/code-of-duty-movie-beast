package com.ticketmonster.movie_beast.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.repositories.ShowRepository;
import com.ticketmonster.movie_beast.services.ShowService;

@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	ShowRepository showRepository;

	@Override
	public List<Show> getAllShows() {
		return showRepository.findAll();
	}

	@Override
	public Show getShowById(@PathVariable(value = "id") Integer id) {
		return showRepository.findByShowId(id);
	}

	@Override
	public Show updateShow(Show show) {
		
		return showRepository.save(show);
	}

	@Override
	public void deleteShow(@PathVariable(value = "id") Integer id) {
		Show show = showRepository.findByShowId(id);
		showRepository.delete(show);
	}
}
