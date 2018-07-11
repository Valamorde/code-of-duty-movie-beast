package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.*;
import com.ticketmonster.moviebeast.repositories.IBookingRepository;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import com.ticketmonster.moviebeast.repositories.IShowRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, User user) {
        User authUser = userRepository.findByEmail(authentication.getName());
        User targetUser = userRepository.getOne(user.getUserId());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            List<SeatReservation> seatReservationList = seatReservationRepository.findAllByUser(targetUser);

            for (int i = 0; i < seatReservationList.size(); i++) {
                SeatReservation seatReservation = seatReservationList.get(i);
                seatReservation.setSeatReserved(false);
                seatReservation.setSeatPaid(false);
                seatReservation.setUser(null);
                seatReservation.setBooking(null);

                if (seatReservation.getBooking().getBookingId() != null) {
                    Booking booking = bookingRepository.getOne(seatReservation.getBooking().getBookingId());
                    bookingRepository.delete(booking);
                }

                seatReservation.setBooking(null);
                Show show = showRepository.getOne(seatReservation.getShow().getShowId());
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
            user.setRole(Role.ROLE_USER.name());
            user.setEnabled(true);
            user.setLastPasswordResetDate(new Date());
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleUser(Authentication authentication, User targetUser) {
        User authUser = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            return new ResponseEntity<>(userRepository.getOne(targetUser.getUserId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> getLoggedUser(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getUserBasket(Authentication authentication, User targetUser) {
        User authUser = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            return new ResponseEntity<>(seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIs(targetUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllUserBookings(Authentication authentication, User targetUser) {
        User authUser = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            return new ResponseEntity<>(bookingRepository.findAllByUser(targetUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleUserBooking(Authentication authentication, User targetUser, Booking booking) {
        User authUser = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            return new ResponseEntity<>(bookingRepository.getOne(booking.getBookingId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleUser(Authentication authentication, User targetUser, User userDetails) {
        User authUser = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(targetUser, authUser)) {
            targetUser.setFullName(userDetails.getFullName());
            targetUser.setEmail(userDetails.getEmail());
            targetUser.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
            targetUser.setRole(userDetails.getRole());
            targetUser.setBookings(userDetails.getBookings());
            targetUser.setSeatReservations(userDetails.getSeatReservations());
            targetUser.setEnabled(userDetails.isEnabled());
            return new ResponseEntity<>(userRepository.save(targetUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> resetPassword(Authentication authentication, User targetUser) {
        User authUser = userRepository.findByEmail(authentication.getName());

        if (customAccessHandler.userIsAdmin(authUser)) {
            targetUser.setPassword(bCryptPasswordEncoder.encode("1234"));
            return new ResponseEntity<>(userRepository.save(targetUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
