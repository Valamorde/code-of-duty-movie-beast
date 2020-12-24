package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.Show;
import com.ticketmonster.moviebeast.repository.ShowRepository;
import com.ticketmonster.moviebeast.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ShowMediator {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowService showService;

    public ResponseEntity<?> getAllShows() {
        return showService.getAllShows();
    }

    public ResponseEntity<?> getSingleShow(Long showId) {
        Show show = showRepository.getOne(showId);
        return showService.getSingleShow(show);
    }

    public ResponseEntity<?> getSeatsByShow(Long showId) {
        Show show = showRepository.getOne(showId);
        return showService.getSeatsByShow(show);
    }

    public ResponseEntity<?> createNewShow(Show newShow, Authentication authentication) {
        return showService.createNewShow(newShow, authentication);
    }

    public ResponseEntity<?> updateSingleShow(Long showId, Show showDetails, Authentication authentication) {
        Show show = showRepository.getOne(showId);
        return showService.updateSingleShow(show, showDetails, authentication);
    }

    public ResponseEntity<?> deleteSingleShow(Long showId, Authentication authentication) {
        Show show = showRepository.getOne(showId);
        return showService.deleteSingleShow(show, authentication);
    }
}
