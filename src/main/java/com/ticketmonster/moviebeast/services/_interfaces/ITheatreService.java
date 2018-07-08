package com.ticketmonster.moviebeast.services._interfaces;

import com.ticketmonster.moviebeast.models.Theatre;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ITheatreService {

    ResponseEntity<?> getAllTheatres();

    ResponseEntity<?> getSingleTheatre(Theatre theatre);

    ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication);

    ResponseEntity<?> updateSingleTheatre(Theatre theatre, Theatre theatreDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleTheatre(Theatre theatre, Authentication authentication);
}
