package com.ticketmonster.moviebeast.controllers.middleware;

import com.ticketmonster.moviebeast.models.Theatre;
import com.ticketmonster.moviebeast.repositories.ITheatreRepository;
import com.ticketmonster.moviebeast.services.implementations.TheatreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The Theatre Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.moviebeast.services.*.*Theatre*
 * ~>   com.ticketmonster.moviebeast.controllers.rest.Theatre*
 */
@Component
public class TheatreMediator {

    @Autowired
    private ITheatreRepository theatreRepository;
    @Autowired
    private TheatreServiceImpl theatreService;

    public ResponseEntity<?> getAllTheatres() {
        return theatreService.getAllTheatres();
    }

    public ResponseEntity<?> getSingleTheatre(Integer theatreId) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.getSingleTheatre(theatre);
    }

    public ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication) {
        return theatreService.createNewTheatre(newTheatre, authentication);
    }

    public ResponseEntity<?> updateSingleTheatre(Integer theatreId, Theatre theatreDetails, Authentication authentication) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.updateSingleTheatre(theatre, theatreDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleTheatre(Integer theatreId, Authentication authentication) {
        Theatre theatre = theatreRepository.getOne(theatreId);
        return theatreService.deleteSingleTheatre(theatre, authentication);
    }
}
