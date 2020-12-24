package com.ticketmonster.moviebeast.rest.middleware;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.BookingRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.BookingService;
import com.ticketmonster.moviebeast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@Service
public class UserMediator {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;

    public void createNewUser(User newUser) {
        userService.createNewUser(newUser);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User getUser(Authentication authentication, Long userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getSingleUser(authentication, targetUser);
    }

    public User getLoggedUser(Principal principal) {
        return userService.getLoggedUser(principal);
    }

    public List<SeatReservation> getUserBasket(Authentication authentication, Long userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getUserBasket(authentication, targetUser);
    }

    public List<Booking> getBookings(Authentication authentication, Long userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getBookings(authentication, targetUser);
    }

    public Booking getBooking(Authentication authentication, Long bookingId) {
        return userService.getBooking(authentication, bookingId);
    }

    public void updateSingleUser(Authentication authentication, Long userId, User userDetails) {
        User targetUser = userRepository.getOne(userId);
        userService.updateSingleUser(authentication, targetUser, userDetails);
    }

    public void resetPassword(Authentication authentication, Long userId) {
        User targetUser = userRepository.getOne(userId);
        userService.resetPassword(authentication, targetUser);
    }

    public void deleteUser(Authentication authentication, Long userId) {
        User targetUser = userRepository.getOne(userId);
        userService.deleteUser(authentication, targetUser);
    }

    public void printTicket(Authentication authentication, HttpServletResponse res, HttpServletRequest req) {
        User user = userRepository.findByEmail(authentication.getName());
        bookingService.printTickets(user, res, req);
    }

    public void logoutUser(Authentication authentication) {
        userService.logoutUser(authentication);
    }
}
