package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.Show;
import com.ticketmonster.moviebeast.rest.middleware.ShowMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ShowController extends AbstractController {

    @Autowired
    private ShowMediator showMediator;

    @GetMapping(value = "/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllShows() {
        try {
            return showMediator.getAllShows();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShowById(@PathVariable(value = "showId") Long showId) {
        try {
            return showMediator.getSingleShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/shows/{showId}/seats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSeatsByShow(@PathVariable(value = "showId") Long showId) {
        try {
            return showMediator.getSeatsByShow(showId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/admin/shows", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewShow(@Valid @RequestBody Show newShow) {
        try {
            return showMediator.createNewShow(newShow, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/admin/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateShow(@PathVariable(value = "showId") Long showId, @Valid @RequestBody Show showDetails) {
        try {
            return showMediator.updateSingleShow(showId, showDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/admin/shows/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShow(@PathVariable(value = "showId") Long showId) {
        try {
            return showMediator.deleteSingleShow(showId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
