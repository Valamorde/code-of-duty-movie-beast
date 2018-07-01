package com.ticketmonster.movie_beast.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.repositories.TheatreRepository;
import com.ticketmonster.movie_beast.services.TheatreService;

@Service
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	TheatreRepository theatreRepository;

	@Override
	public List<Theatre> getAllTheatres() {
		return theatreRepository.findAll();
	}

	@Override
	public Theatre getTheatreById(Integer id) {
		return theatreRepository.findByTheatreId(id);
	}

	@Override
	public Theatre updateTheatre(Theatre theatre) {
		return theatreRepository.save(theatre);
	}

	@Override
	public void deleteTheatre(Integer id) {
		Theatre theatre = theatreRepository.findByTheatreId(id);
		theatreRepository.delete(theatre);

	}

}
