package com.ticketmonster.moviebeast.controllers.middleware;

import com.ticketmonster.moviebeast.models.Booking;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IBookingRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services.implementations.BookingServiceImpl;
import com.ticketmonster.moviebeast.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The User Mediator sits between the REST & Service Layers
 * and is responsible to map an ID to an Entity and perform any required checks
 * before passing it down to the Business Layer.
 * See ~>   com.ticketmonster.moviebeast.services.*.*User*
 *     ~>   com.ticketmonster.moviebeast.controllers.rest.User*
 */
@Component
public class UserMediator {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private BookingServiceImpl bookingService;

    public ResponseEntity<?> createNewUser(User newUser) {
        return userService.createNewUser(newUser);
    }

    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    public ResponseEntity<?> getSingleUser(Authentication authentication, Integer userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getSingleUser(authentication, targetUser);
    }

    public ResponseEntity<?> getUserBasket(Authentication authentication, Integer userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getUserBasket(authentication, targetUser);
    }

    public ResponseEntity<?> getAllUserBookings(Authentication authentication, Integer userId) {
        User targetUser = userRepository.getOne(userId);
        return userService.getAllUserBookings(authentication, targetUser);
    }

    public ResponseEntity<?> getSingleUserBooking(Authentication authentication, Integer userId, Integer bookingId) {
        User targetUser = userRepository.getOne(userId);
        Booking booking = bookingRepository.getOne(bookingId);
        return userService.getSingleUserBooking(authentication, targetUser, booking);
    }

    public ResponseEntity<?> updateSingleUser(Authentication authentication, Integer userId, User userDetails) {
        User targetUser = userRepository.getOne(userId);
        return userService.updateSingleUser(authentication, targetUser, userDetails);
    }

    public ResponseEntity<?> resetPassword(Authentication authentication, Integer userId){
        User targetUser = userRepository.getOne(userId);
        return userService.resetPassword(authentication, targetUser);
    }

    public ResponseEntity<?> deleteUserAndCleanup(Authentication authentication, Integer userId){
        User targetUser = userRepository.getOne(userId);
        return userService.deleteUserAndCleanup(authentication, targetUser);
    }


    public void printTicket(Authentication authentication, HttpServletResponse res, HttpServletRequest req){
        User user = userRepository.findByEmail(authentication.getName());
        bookingService.printTickets(user,res,req);
    }
}
