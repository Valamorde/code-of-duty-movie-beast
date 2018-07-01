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

import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.services.ShowService;

@Component
@RestController
public class ShowController {

	@Autowired
	private ShowService showService;

	// Get All Shows
	@GetMapping("/shows")
	public ResponseEntity<List<Show>> getAllShows() {
		List<Show> list = showService.getAllShows();
		return new ResponseEntity<List<Show>>(list, HttpStatus.OK);
	}

	// Get a Single Show
	@GetMapping("/shows/{id}")
	public ResponseEntity<Show> getShowById(@PathVariable(value = "id") Integer id) {
		try {
			Show show = showService.getShowById(id);
			return new ResponseEntity<Show>(show, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Update a Show
	@PutMapping("/shows/{id}")
	public ResponseEntity<Show> updateShow(@PathVariable(value = "id") Integer id, @Valid @RequestBody Show showDetails) {
		try {
			Show show = showService.getShowById(id);
			show.setAvailableSeats(showDetails.getAvailableSeats());
			showService.updateShow(show);
			return new ResponseEntity<Show>(show, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Delete a Show
	@DeleteMapping("/shows/{id}")
	public ResponseEntity<?> deleteShow(@PathVariable(value = "id") Integer id) {
		showService.deleteShow(id);
		return ResponseEntity.ok().build();
	}
}
