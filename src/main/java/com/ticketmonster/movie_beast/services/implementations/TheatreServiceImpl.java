package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Theatre;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.ITheatreRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.ITheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TheatreServiceImpl implements ITheatreService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITheatreRepository theatreRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Override
    public ResponseEntity<?> getAllTheatres() {
        return null;
    }

    @Override
    public ResponseEntity<?> getSingleTheatre(Integer theatreId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Theatre theatre = new Theatre();
            theatre.setTheatreName(newTheatre.getTheatreName());
            theatre.setCityId(newTheatre.getCityId());
            theatre.setMovieId(newTheatre.getMovieId());
            theatre.setTheatreAddress(newTheatre.getTheatreAddress());
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> updateSingleTheatre(Integer theatreId, Theatre theatreDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Theatre theatre = theatreRepository.getOne(theatreId);
            theatre.setTheatreName(theatreDetails.getTheatreName());
            theatre.setCityId(theatreDetails.getCityId());
            theatre.setMovieId(theatreDetails.getMovieId());
            theatre.setTheatreAddress(theatreDetails.getTheatreAddress());
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> deleteSingleTheatre(Integer theatreId, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            theatreRepository.delete(theatreRepository.getOne(theatreId));
            return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
