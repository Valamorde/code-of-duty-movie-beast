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
    IUserRepository userRepository;

    @Autowired
    IShowRepository showRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> getAllShows() {
        return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleShow(Integer showId) {
        return new ResponseEntity<>(showRepository.getOne(showId), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewShow(Show newShow, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Show show = new Show();
            show.setAvailableSeats(newShow.getAvailableSeats());
            show.setInitialSeats(newShow.getInitialSeats());
            show.setMovieId(newShow.getMovieId());
            show.setShowCost(newShow.getShowCost());
            show.setShowDate(newShow.getShowDate());
            return new ResponseEntity<>(showRepository.save(show), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleShow(Integer showId, Show showDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Show show = showRepository.getOne(showId);
            show.setAvailableSeats(showDetails.getAvailableSeats());
            show.setInitialSeats(showDetails.getInitialSeats());
            show.setMovieId(showDetails.getMovieId());
            show.setShowCost(showDetails.getShowCost());
            show.setShowDate(showDetails.getShowDate());
            return new ResponseEntity<>(showRepository.save(show), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleShow(Integer showId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            showRepository.delete(showRepository.getOne(showId));
            return new ResponseEntity<>(showRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
