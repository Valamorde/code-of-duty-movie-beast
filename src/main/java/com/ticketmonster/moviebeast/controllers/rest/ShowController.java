package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.ShowMediator;
import com.ticketmonster.moviebeast.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This Rest Controller is responsible for the Shows category.
 * It allows the user to view all or a single show and the admin to create, update or delete a specific or all shows.
 *
 * @author nancyatnic
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ShowController {

    @Autowired
    private ShowMediator showMediator;

    //<editor-fold desc="User Operations">

    /**
     * Allows the user to view all the shows
     *
     * @return gets all shows, or returns bad request
     */
    @GetMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllShows() {
        try {
            return showMediator.getAllShows();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single show
     *
     * @param showId
     * @return returns specific show
     */
    @GetMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowById(@PathVariable(value = "showId") Integer showId) {
        try {
            return showMediator.getSingleShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to view a single show's seats
     *
     * @param showId
     * @return a list of the specified show's seats
     */
    @GetMapping(value = "/shows/{showId}/seats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSeatsByShow(@PathVariable(value = "showId") Integer showId) {
        try {
            return showMediator.getSeatsByShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Admin Operations">

    /**
     * Allows the admin to create a new show
     *
     * @param newShow
     * @return saves new show, or status error
     */
    @PostMapping(value = "/admin/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewShow(@Valid @RequestBody Show newShow) {
        try {
            return showMediator.createNewShow(newShow, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to update a show
     *
     * @param showId
     * @param showDetails
     * @return saves updates, or status error
     */
    @PutMapping(value = "/admin/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShow(@PathVariable(value = "showId") Integer showId, @Valid @RequestBody Show showDetails) {
        try {
            return showMediator.updateSingleShow(showId, showDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the admin to delete a show
     *
     * @param showId
     * @return list of current shows, or status error
     */
    @DeleteMapping(value = "/admin/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShow(@PathVariable(value = "showId") Integer showId) {
        try {
            return showMediator.deleteSingleShow(showId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>
}
