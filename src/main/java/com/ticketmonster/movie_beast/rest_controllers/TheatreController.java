package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.services._interfaces.ITheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@Component
@RestController
public class TheatreController {

    @Autowired
    ITheatreService theatreService;

    // Get All Theatres
    @GetMapping("/theatres/all")
    @Produces("application/json")
    public ResponseEntity<?> getAllTheatres() {
        try {
            return theatreService.getAllTheatres();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get a Single Theatre
    @GetMapping("/theatres/{theatreId}")
    @Produces("application/json")
    public ResponseEntity<?> getTheatreById(@PathVariable(value = "theatreId") Integer theatreId) {
        try {
            return theatreService.getSingleTheatre(theatreId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Create a New Theatre
    @PostMapping("/theatres")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseEntity<?> createNewTheatre(@Valid @RequestBody Theatre newTheatre) {
        try {
            return theatreService.createNewTheatre(newTheatre, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a Theatre
    @PutMapping("/theatres/{theatreId}")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseEntity<?> updateTheatre(@PathVariable(value = "theatreId") Integer theatreId, @Valid @RequestBody Theatre theatreDetails) {
        try {
            return theatreService.updateSingleTheatre(theatreId, theatreDetails, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a Theatre
    @DeleteMapping("/theatres/{theatreId}")
    @Produces("application/json")
    public ResponseEntity<?> deleteTheatre(@PathVariable(value = "theatreId") Integer theatreId) {
        try {
            return theatreService.deleteSingleTheatre(theatreId, SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
