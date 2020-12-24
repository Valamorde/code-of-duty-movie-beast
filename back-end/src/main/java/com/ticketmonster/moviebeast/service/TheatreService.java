package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.Theatre;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TheatreService {

    ResponseEntity<?> getAllTheatres();

    ResponseEntity<?> getSingleTheatre(Theatre theatre);

    ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication);

    ResponseEntity<?> updateSingleTheatre(Theatre theatre, Theatre theatreDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleTheatre(Theatre theatre, Authentication authentication);
}
