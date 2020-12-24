package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.Show;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.ShowRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.ShowService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("showService")
public class ShowServiceImpl implements ShowService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

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
        return new ResponseEntity<>(showRepository.getOne(show.getId()), HttpStatus.OK);
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
            show.setCost(newShow.getCost());
            show.setDate(newShow.getDate());
            show.setMovie(newShow.getMovie());
            logger.info("Created new Show for Movie:[" + show.getMovie().getName() + "].");
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
            show.setCost(showDetails.getCost());
            show.setDate(showDetails.getDate());
            show.setSeats(showDetails.getSeats());
            logger.info("Updated Show with ID:[" + show.getId() + "].");
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
            logger.info("Deleted Show with ID:[" + show.getId() + "].");
            return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
