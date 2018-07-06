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
    private IUserRepository userRepository;

    @Autowired
    private ITheatreRepository theatreRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    public ResponseEntity<?> getAllTheatres() {
        return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSingleTheatre(Theatre theatre) {
        return new ResponseEntity<>(theatreRepository.getOne(theatre.getTheatreId()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createNewTheatre(Theatre newTheatre, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            Theatre theatre = new Theatre();
            theatre.setTheatreName(newTheatre.getTheatreName());
            theatre.setCity(newTheatre.getCity());
            theatre.setTheatreAddress(newTheatre.getTheatreAddress());
            theatre.setMovies(newTheatre.getMovies());
            return new ResponseEntity<>(theatreRepository.save(theatre), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> updateSingleTheatre(Theatre theatre, Theatre theatreDetails, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            theatre.setTheatreName(theatreDetails.getTheatreName());
            theatre.setCity(theatreDetails.getCity());
            theatre.setTheatreAddress(theatreDetails.getTheatreAddress());
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
            return new ResponseEntity<>(theatreRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
