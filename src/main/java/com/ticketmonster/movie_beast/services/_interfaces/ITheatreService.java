package com.ticketmonster.movie_beast.services._interfaces;

import com.ticketmonster.movie_beast.models.Theatre;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ITheatreService {

    ResponseEntity<?> getAllTheatres();

    ResponseEntity<?> getSingleTheatre(Integer theatreId);

    ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication);

    ResponseEntity<?> updateSingleTheatre(Integer theatreId, Theatre theatreDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleTheatre(Integer theatreId, Authentication authentication);
}
