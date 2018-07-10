package com.ticketmonster.moviebeast.controllers.rest;

import com.ticketmonster.moviebeast.controllers.middleware.BookingMediator;
import com.ticketmonster.moviebeast.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * This Rest Controller is responsible for the tickets' booking and the rest of the actions following.
 * It allows the user to book(pay) his reserved seats, cancel a booking, view all of his bookings or a single one, by id search.
 * It also allows the admin to update and delete any booking.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class BookingController {

    @Autowired
    private BookingMediator bookingMediator;

    /**
     * Allows the user to book his reserved seats
     *
     * @return user's booking
     */
    @PostMapping(value = "/checkoutBasket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkout() {
        try {
            return bookingMediator.bookAllInBasket(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to cancel a Booked Ticket
     *
     * @param booking
     * @return user's current booking list
     */
    @PostMapping(value = "/cancelBooking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking) {
        try {
            return bookingMediator.cancelTicket(SecurityContextHolder.getContext().getAuthentication(), booking);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to get all his Bookings
     *
     * @return list of total user bookings, or status error
     */
    @GetMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBookings() {
        try {
            return bookingMediator.getAll(SecurityContextHolder.getContext().getAuthentication());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows the user to get a Single Booking
     *
     * @param bookingId
     * @return user's single booking, or status error
     */
    @GetMapping(value = "/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookingById(@PathVariable(value = "bookingId") Integer bookingId) {
        try {
            return bookingMediator.getBooking(SecurityContextHolder.getContext().getAuthentication(), bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //<editor-fold desc="Admin Operations">
    /**
     * Allows the admin to print a bookings report
     *
     * @param req - incoming request
     * @param res - outgoing response
     * @return Http Status
     */
    @GetMapping(value = "/admin/bookingsReport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> printTicketReport(HttpServletRequest req, HttpServletResponse res) {
        bookingMediator.printTicketReport(SecurityContextHolder.getContext().getAuthentication(), res, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Allows an admin to update a Booking
     *
     * @param bookingId
     * @param bookingDetails
     * @return saves booking changes (status ok), or status error
     */
    @PutMapping(value = "/admin/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBooking(@PathVariable(value = "bookingId") Integer bookingId, @Valid @RequestBody Booking bookingDetails) {
        try {
            return bookingMediator.updateBooking(SecurityContextHolder.getContext().getAuthentication(), bookingId, bookingDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Allows an admin to delete a Booking
     *
     * @param bookingId
     * @return current list of user's bookings, or status error
     */
    @DeleteMapping(value = "/admin/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBooking(@PathVariable(value = "bookingId") Integer bookingId) {
        try {
            return bookingMediator.deleteBooking(SecurityContextHolder.getContext().getAuthentication(), bookingId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //</editor-fold>
}
