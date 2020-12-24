package com.ticketmonster.moviebeast.rest;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.rest.middleware.BookingMediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class BookingController extends AbstractController {

    @Autowired
    private BookingMediator bookingMediator;

    @PostMapping(
            value = "/checkoutBasket"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkout() {
        bookingMediator.bookAllInBasket(SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping(
            value = "/cancelBooking",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@Valid @RequestBody Booking booking) {
        bookingMediator.cancelTicket(SecurityContextHolder.getContext().getAuthentication(), booking);
    }

    @GetMapping(
            value = "/bookings",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Booking> getAllBookings() {
        return bookingMediator.getAll(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping(
            value = "/bookings/{bookingId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Booking getBooking(@PathVariable(value = "bookingId") Long bookingId) {
        return bookingMediator.getBooking(
                SecurityContextHolder.getContext().getAuthentication(),
                bookingId
        );
    }

    @GetMapping(
            value = "/admin/bookingsReport",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public void printTicketReport(HttpServletRequest req, HttpServletResponse res) {
        bookingMediator.printTicketReport(
                SecurityContextHolder.getContext().getAuthentication(),
                res,
                req
        );
    }

    @PutMapping(
            value = "/admin/bookings/{bookingId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBooking(@PathVariable(value = "bookingId") Long bookingId, @Valid @RequestBody Booking bookingDetails) {
        bookingMediator.updateBooking(
                SecurityContextHolder.getContext().getAuthentication(),
                bookingId,
                bookingDetails
        );
    }

    @DeleteMapping(
            value = "/admin/bookings/{bookingId}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable(value = "bookingId") Long bookingId) {
        bookingMediator.deleteBooking(
                SecurityContextHolder.getContext().getAuthentication(),
                bookingId
        );
    }
}
