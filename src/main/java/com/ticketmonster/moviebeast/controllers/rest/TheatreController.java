package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.TheatreMediator;
import com.ticketmonster.moviebeast.models.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This Rest Controller is responsible for the Theatre category.
 * It allows the user to view all or a single theatre and the admin to create, update or delete a specific or all theatres.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TheatreController {

    @Autowired
    private TheatreMediator theatreMediator;

    /**
     * Allows the user to view all the theatres
     *
     * @return list of theatres, or status error
     */
    @GetMapping(value = "/theatres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTheatres() {
        try {
            return theatreMediator.getAllTheatres();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single theatre
     *
     * @param theatreId
     * @return specific theatre, or status error
     */
    @GetMapping(value = "/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTheatreById(@PathVariable(value = "theatreId") Integer theatreId) {
        try {
            return theatreMediator.getSingleTheatre(theatreId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to create a new theatre
     *
     * @param newTheatre
     * @return saves new theatre, or status error
     */
    @PostMapping(value = "/theatres", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewTheatre(@Valid @RequestBody Theatre newTheatre) {
        try {
            return theatreMediator.createNewTheatre(newTheatre, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to update a theatre
     *
     * @param theatreId
     * @param theatreDetails
     * @return saves updates, or status error
     */
    @PutMapping(value = "/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTheatre(@PathVariable(value = "theatreId") Integer theatreId, @Valid @RequestBody Theatre theatreDetails) {
        try {
            return theatreMediator.updateSingleTheatre(theatreId, theatreDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to delete a theatre
     *
     * @param theatreId
     * @return list of current theatres, or status error
     */
    @DeleteMapping(value = "/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTheatre(@PathVariable(value = "theatreId") Integer theatreId) {
        try {
            return theatreMediator.deleteSingleTheatre(theatreId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
