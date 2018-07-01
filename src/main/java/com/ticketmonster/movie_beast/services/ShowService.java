package com.ticketmonster.movie_beast.services;

import java.util.List;

import com.ticketmonster.movie_beast.models.Show;

public interface ShowService {

	List<Show> getAllShows();
	Show getShowById(Integer id);
	Show updateShow(Show show);
	void deleteShow(Integer id);
}
