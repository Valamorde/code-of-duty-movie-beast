package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.Theatre;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.TheatreRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.TheatreService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("theatreService")
public class TheatreServiceImpl implements TheatreService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    public ResponseEntity<?> getAllTheatres() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSingleTheatre(Theatre theatre) {
        return new ResponseEntity<>(theatreRepository.getOne(theatre.getId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Theatre theatre = new Theatre();
            theatre.setName(newTheatre.getName());
            theatre.setCity(newTheatre.getCity());
            theatre.setAddress(newTheatre.getAddress());
            theatre.setMovies(newTheatre.getMovies());
            logger.info("Created new Theatre with Name:[" + theatre.getName() + "].");
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> updateSingleTheatre(Theatre theatre, Theatre theatreDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            theatre.setName(theatreDetails.getName());
            theatre.setCity(theatreDetails.getCity());
            theatre.setAddress(theatreDetails.getAddress());
            logger.info("Updated Theatre with ID:[" + theatre.getId() + "].");
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> deleteSingleTheatre(Theatre theatre, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            theatreRepository.delete(theatre);
            logger.info("Deleted Theatre with ID:[" + theatre.getId() + "].");
            return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
