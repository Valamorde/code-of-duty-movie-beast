package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.services._interfaces.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class ShowController {

    @Autowired
    IShowService showService;

    // Get All Shows
    @GetMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllShows() {
        try {
            return showService.getAllShows();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Show
    @GetMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowById(@PathVariable(value = "showId") Integer showId) {
        try {
            return showService.getSingleShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New Show
    @PostMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewShow(@Valid @RequestBody Show newShow) {
        try {
            return showService.createNewShow(newShow, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Show
    @PutMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShow(@PathVariable(value = "showId") Integer showId, @Valid @RequestBody Show showDetails) {
        try {
            return showService.updateSingleShow(showId, showDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a Show
    @DeleteMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShow(@PathVariable(value = "showId") Integer showId) {
        try {
            return showService.deleteSingleShow(showId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
