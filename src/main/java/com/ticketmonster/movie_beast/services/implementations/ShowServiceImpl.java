package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.IShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShowServiceImpl implements IShowService {

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
            return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
