package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.Theatre;
import com.ticketmonster.moviebeast.repository.TheatreRepository;
import com.ticketmonster.moviebeast.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TheatreMediator {

    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TheatreService theatreService;

    public ResponseEntity<?> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    public ResponseEntity<?> getSingleTheatre(Long theatreId) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.getSingleTheatre(theatre);
    }

    public ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication) {
        return theatreService.createNewTheatre(newTheatre, authentication);
    }

    public ResponseEntity<?> updateSingleTheatre(Long theatreId, Theatre theatreDetails, Authentication authentication) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.updateSingleTheatre(theatre, theatreDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleTheatre(Long theatreId, Authentication authentication) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.deleteSingleTheatre(theatre, authentication);
    }
}
