package com.ticketmonster.movie_beast.controllers.rest;

import com.ticketmonster.movie_beast.controllers.middleware.ShowMediator;
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

@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ShowController {

    @Autowired
    private ShowMediator showMediator;

    // Get All Shows
    @GetMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllShows() {
        try {
            return showMediator.getAllShows();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Show
    @GetMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowById(@PathVariable(value = "showId") Integer showId) {
        try {
            return showMediator.getSingleShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New Show
    @PostMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewShow(@Valid @RequestBody Show newShow) {
        try {
            return showMediator.createNewShow(newShow, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Show
    @PutMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShow(@PathVariable(value = "showId") Integer showId, @Valid @RequestBody Show showDetails) {
        try {
            return showMediator.updateSingleShow(showId, showDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a Show
    @DeleteMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShow(@PathVariable(value = "showId") Integer showId) {
        try {
            return showMediator.deleteSingleShow(showId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
