package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.*;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IBookingRepository bookingRepository;

    @Autowired
    ISeatReservationRepository seatReservationRepository;

    @Autowired
    IShowRepository showRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, Integer userId) {
        User authUser = userRepository.findByEmail(authentication.getName());
        User targetUser = userRepository.getOne(userId);

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, authUser)) {
            List<SeatReservation> seatReservationList = seatReservationRepository.findAllByUserId(targetUser.getUserId());

            for (int i = 0; i < seatReservationList.size(); i++) {
                SeatReservation seatReservation = seatReservationList.get(i);
                seatReservation.setSeatReserved(false);
                seatReservation.setSeatPaid(false);
                seatReservation.setUserId(null);

                if (seatReservation.getBookingId() != null) {
                    Booking booking = bookingRepository.findByBookingId(seatReservation.getBookingId());
                    bookingRepository.delete(booking);
                }

                seatReservation.setBookingId(null);
                Show show = showRepository.findByShowId(seatReservation.getShowId());
                show.setAvailableSeats(show.getAvailableSeats() + 1);
                showRepository.save(show);
                seatReservationRepository.save(seatReservation);
            }
            userRepository.delete(targetUser);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> createNewUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER.name());
            user.setEnabled(true);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> findSingleUser(Authentication authentication, Integer userId) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, user)) {
            return new ResponseEntity<>(userRepository.findById(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getUserBasket(Authentication authentication, Integer userId) {
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, user)) {
            return new ResponseEntity<>(seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllUserBookings(Authentication authentication, Integer userId) {
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, user)) {
            return new ResponseEntity<>(bookingRepository.findAllByUserId(userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleUserBooking(Authentication authentication, Integer userId, Integer bookingId) {
        User user = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, user)) {
            return new ResponseEntity<>(bookingRepository.findByBookingIdAndUserId(bookingId, userId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleUser(Authentication authentication, Integer userId, User userDetails) {
        User authUser = userRepository.findByEmail(authentication.getName());
        User targetUser = userRepository.getOne(userId);

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(userId, authUser)) {
            targetUser.setFullName(userDetails.getFullName());
            targetUser.setEmail(userDetails.getEmail());
            targetUser.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
            return new ResponseEntity<>(userRepository.save(targetUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
