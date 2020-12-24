package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.Theatre;
import com.ticketmonster.moviebeast.rest.middleware.TheatreMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TheatreController extends AbstractController {

    @Autowired
    private TheatreMediator theatreMediator;

    @GetMapping(value = "/theatres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTheatres() {
        try {
            return theatreMediator.getAllTheatres();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTheatreById(@PathVariable(value = "theatreId") Long theatreId) {
        try {
            return theatreMediator.getSingleTheatre(theatreId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/admin/theatres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewTheatre(@Valid @RequestBody Theatre newTheatre) {
        try {
            return theatreMediator.createNewTheatre(newTheatre, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/admin/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTheatre(@PathVariable(value = "theatreId") Long theatreId, @Valid @RequestBody Theatre theatreDetails) {
        try {
            return theatreMediator.updateSingleTheatre(theatreId, theatreDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/admin/theatres/{theatreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTheatre(@PathVariable(value = "theatreId") Long theatreId) {
        try {
            return theatreMediator.deleteSingleTheatre(theatreId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
