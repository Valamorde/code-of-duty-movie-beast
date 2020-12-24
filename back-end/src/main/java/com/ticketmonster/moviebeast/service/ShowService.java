package com.ticketmonster.moviebeast.service;

import com.ticketmonster.moviebeast.model.Show;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ShowService {

    ResponseEntity<?> getAllShows();

    ResponseEntity<?> getSingleShow(Show show);

    ResponseEntity<?> getSeatsByShow(Show show);

    ResponseEntity<?> createNewShow(Show newShow, Authentication authentication);

    ResponseEntity<?> updateSingleShow(Show show, Show showDetails, Authentication authentication);

    ResponseEntity<?> deleteSingleShow(Show show, Authentication authentication);
}
