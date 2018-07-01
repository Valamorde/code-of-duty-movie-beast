package com.ticketmonster.movie_beast.services;

import java.util.List;

import com.ticketmonster.movie_beast.models.Theatre;

public interface TheatreService {
	 List<Theatre> getAllTheatres();
	 Theatre getTheatreById(Integer id);
	 Theatre updateTheatre(Theatre theatre);
	 void deleteTheatre(Integer id);
}
