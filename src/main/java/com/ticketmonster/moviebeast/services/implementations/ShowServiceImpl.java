package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.Show;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IShowRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.IShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This ShowServiceImpl is responsible for the actions regarding the shows.
 * Namely, fetches info when a user asks for all or a single show, while allows the admin to create, update and delete a show.
 */
@Service
public class ShowServiceImpl implements IShowService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> getAllShows() {
        return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleShow(Show show) {
        return new ResponseEntity<>(showRepository.getOne(show.getShowId()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSeatsByShow(Show show) {
        return new ResponseEntity<>(show.getSeats(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewShow(Show newShow, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Show show = new Show();
            show.setAvailableSeats(newShow.getAvailableSeats());
            show.setInitialSeats(newShow.getInitialSeats());
            show.setMovie(newShow.getMovie());
            show.setShowCost(newShow.getShowCost());
            show.setShowDate(newShow.getShowDate());
            show.setMovie(newShow.getMovie());
            logger.info("Created new Show for Movie:[" + show.getMovie().getMovieName() + "].");
            return new ResponseEntity<>(showRepository.save(show), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleShow(Show show, Show showDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            show.setAvailableSeats(showDetails.getAvailableSeats());
            show.setInitialSeats(showDetails.getInitialSeats());
            show.setMovie(showDetails.getMovie());
            show.setShowCost(showDetails.getShowCost());
            show.setShowDate(showDetails.getShowDate());
            show.setSeats(showDetails.getSeats());
            logger.info("Updated Show with ID:[" + show.getShowId() + "].");
            return new ResponseEntity<>(showRepository.save(show), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleShow(Show show, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            showRepository.delete(show);
            logger.info("Deleted Show with ID:[" + show.getShowId() + "].");
            return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
