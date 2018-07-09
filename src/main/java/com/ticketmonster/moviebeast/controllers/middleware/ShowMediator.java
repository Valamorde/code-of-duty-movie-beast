package com.ticketmonster.moviebeast.controllers.middleware;

import com.ticketmonster.moviebeast.models.Show;
import com.ticketmonster.moviebeast.repositories.IShowRepository;
import com.ticketmonster.moviebeast.services.implementations.ShowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The Show Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.moviebeast.services.*.*Show*
 *     ~>   com.ticketmonster.moviebeast.controllers.rest.Show*
 */
@Component
public class ShowMediator {

    @Autowired
    private IShowRepository showRepository;
    @Autowired
    private ShowServiceImpl showService;

    public ResponseEntity<?> getAllShows() {
        return showService.getAllShows();
    }

    public ResponseEntity<?> getSingleShow(Integer showId) {
        Show show = showRepository.getOne(showId);
        return showService.getSingleShow(show);
    }

    public ResponseEntity<?> getSeatsByShow(Integer showId){
        Show show = showRepository.getOne(showId);
        return showService.getSeatsByShow(show);
    }

    public ResponseEntity<?> createNewShow(Show newShow, Authentication authentication) {
        return showService.createNewShow(newShow, authentication);
    }

    public ResponseEntity<?> updateSingleShow(Integer showId, Show showDetails, Authentication authentication) {
        Show show = showRepository.getOne(showId);
        return showService.updateSingleShow(show, showDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleShow(Integer showId, Authentication authentication) {
        Show show = showRepository.getOne(showId);
        return showService.deleteSingleShow(show, authentication);
    }
}
