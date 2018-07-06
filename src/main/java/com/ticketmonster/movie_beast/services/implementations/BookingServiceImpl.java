package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IUserRepository;
import com.ticketmonster.movie_beast.services._interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ISeatReservationRepository seatReservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> bookAllInBasket(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(authentication.getName());
        List<SeatReservation> reservedSeats = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIs(user);
        List<Booking> bookings = new ArrayList<>();

        for (int i = 0; i < reservedSeats.size(); i++) {
            if (!customAccessHandler.userIsAuthorizedToViewSpecifiedContent(reservedSeats.get(i).getUser(), user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Booking booking = new Booking();
            booking.setSeatReservation(reservedSeats.get(i));
            booking.setUser(reservedSeats.get(i).getUser());
            booking.setBookingCost(booking.getSeatReservation().getShow().getShowCost());
            booking.setBookingDate(booking.getSeatReservation().getShow().getShowDate());
            bookings.add(bookingRepository.save(booking));

            SeatReservation seatReservation = seatReservationRepository.getOne(reservedSeats.get(i).getSeatId());
            seatReservation.setBooking(bookings.get(i));
            seatReservation.setSeatPaid(true);
            seatReservationRepository.save(seatReservation);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // TODO: add user check
    @Override
    @Transactional
    public ResponseEntity<?> cancelSingleTicket(Booking booking) {

        SeatReservation seatReservation = seatReservationRepository.findByBooking(booking);
        List<Booking> bookingList = bookingRepository.findAllByUser(booking.getUser());
        bookingRepository.delete(booking);
        bookingList.remove(booking);
        seatReservation.setUser(null);
        seatReservation.setBooking(null);
        seatReservation.setSeatPaid(false);
        seatReservation.setSeatReserved(false);
        seatReservationRepository.save(seatReservation);

        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getAllBookings(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getSingleBooking(Authentication authentication, Booking booking) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(booking.getUser(), user)) {
            return new ResponseEntity<>(bookingRepository.getOne(booking.getBookingId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleBooking(Authentication authentication, Booking booking, Booking newBooking) {
        User user = userRepository.findByEmail((authentication.getName()));
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(booking.getUser(), user)) {
            booking.setUser(newBooking.getUser());
            booking.setBookingDate(newBooking.getBookingDate());
            return new ResponseEntity<>(bookingRepository.save(booking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleBooking(Authentication authentication, Booking booking) {
        User user = userRepository.findByEmail((authentication.getName()));
        if (customAccessHandler.userIsAdmin(user)) {
            bookingRepository.delete(booking);
            return new ResponseEntity<>(bookingRepository.findAllByUser(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
