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

import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.services.impl.TheatreServiceImpl;

@Component
@RestController
public class TheatreController {

	@Autowired
	private TheatreServiceImpl TheatreService;

	// Get All Theatres
	@GetMapping("/theatres/all")
	public ResponseEntity<List<Theatre>> getAllTheatres() {
		List<Theatre> list = TheatreService.getAllTheatres();
		return new ResponseEntity<List<Theatre>>(list, HttpStatus.OK);
	}

	// Get a Single Theatre
	@GetMapping("/theatres/{id}")
	public ResponseEntity<Theatre> getTheatreById(@PathVariable(value = "id") Integer id) {
		try {
			Theatre theatre = TheatreService.getTheatreById(id);
			return new ResponseEntity<Theatre>(theatre, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Update a Theatre
	@PutMapping("/theatres/{id}")
	public ResponseEntity<Theatre> updateTheatre(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody Theatre TheatreDetails) {
		try {
			Theatre theatre = TheatreService.getTheatreById(id);
			theatre.setTheatreName(theatre.getTheatreName());
			TheatreService.updateTheatre(theatre);
			return new ResponseEntity<Theatre>(theatre, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Delete a Theatre
	@DeleteMapping("/theatres/{id}")
	public ResponseEntity<?> deleteTheatre(@PathVariable(value = "id") Integer id) {
		TheatreService.deleteTheatre(id);
		return ResponseEntity.ok().build();
	}
}
