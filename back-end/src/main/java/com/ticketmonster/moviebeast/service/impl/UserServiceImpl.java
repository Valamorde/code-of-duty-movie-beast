package com.ticketmonster.moviebeast.service.impl;

import com.ticketmonster.moviebeast.model.*;
import com.ticketmonster.moviebeast.repository.BookingRepository;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import com.ticketmonster.moviebeast.repository.ShowRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.UserService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public void deleteUser(Authentication authentication, User user) {
        User targetUser = userRepository.getOne(user.getId());

        List<SeatReservation> seatReservationList = seatReservationRepository.findAllByUser(targetUser);

        for (SeatReservation seatReservation : seatReservationList) {
            seatReservation.setReserved(false);
            seatReservation.setPaid(false);
            seatReservation.setUser(null);
            seatReservation.setBooking(null);

            if (seatReservation.getBooking().getId() != null) {
                Booking booking = bookingRepository.getOne(seatReservation.getBooking().getId());
                bookingRepository.delete(booking);
            }

            seatReservation.setBooking(null);
            Show show = showRepository.getOne(seatReservation.getShow().getId());
            show.setAvailableSeats(show.getAvailableSeats() + 1);
            showRepository.save(show);
            seatReservationRepository.save(seatReservation);
        }
        userRepository.delete(targetUser);

        logger.info("Cleaned up after and deleted User with ID:[" + targetUser.getId() + "].");
    }

    @Override
    @Transactional
    public void createNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER.name());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        logger.info("Created new User with email:[" + user.getEmail() + "].");

        userRepository.save(user);
    }

    @Override
    public void logoutUser(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        List<SeatReservation> seatReservations = seatReservationRepository.findAllByReservedIsTrueAndPaidIsFalseAndUserIs(user);

        for (SeatReservation seatReservation : seatReservations) {
            seatReservation.setUser(null);
            seatReservation.setReserved(false);
            seatReservationRepository.save(seatReservation);
        }

        logger.info("Logged out User with ID:[" + user.getId() + "].");
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User getSingleUser(Authentication authentication, User targetUser) {
        return userRepository.getOne(targetUser.getId());
    }

    @Override
    @Transactional
    public User getLoggedUser(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

    @Override
    @Transactional
    public List<SeatReservation> getUserBasket(Authentication authentication, User targetUser) {
        return seatReservationRepository.findAllByReservedIsTrueAndPaidIsFalseAndUserIs(targetUser);
    }

    @Override
    @Transactional
    public List<Booking> getBookings(Authentication authentication, User targetUser) {
        return bookingRepository.findAllByUser(targetUser);
    }

    @Override
    @Transactional
    public Booking getBooking(Authentication authentication, Long bookingId) {
        return bookingRepository.getOne(bookingId);
    }

    @Override
    @Transactional
    public void updateSingleUser(Authentication authentication, User targetUser, User userDetails) {
        targetUser.setFullName(userDetails.getFullName());
        targetUser.setEmail(userDetails.getEmail());
        targetUser.setRole(userDetails.getRole());
        targetUser.setBookings(userDetails.getBookings());
        targetUser.setSeatReservations(userDetails.getSeatReservations());
        targetUser.setEnabled(userDetails.isEnabled());

        logger.info("Updated User with ID:[" + targetUser.getId() + "].");
        userRepository.save(targetUser);
    }

    @Override
    @Transactional
    public void resetPassword(Authentication authentication, User targetUser) {
        targetUser.setPassword(bCryptPasswordEncoder.encode("1234"));
        userRepository.save(targetUser);
        logger.info("User with ID:[" + targetUser.getId() + "]'s password was reset.");
    }
}
