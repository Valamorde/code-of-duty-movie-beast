package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
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
    IBookingRepository bookingRepository;

    @Autowired
    ISeatReservationRepository seatReservationRepository;

    @Autowired
    IShowRepository showRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Override
    @Transactional
    public ResponseEntity<?> bookAllInBasket(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(authentication.getName());
        List<SeatReservation> reservedSeats = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(user.getUserId());
        List<Booking> bookings = new ArrayList<>();

        for (int i = 0; i < reservedSeats.size(); i++) {
            if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(reservedSeats.get(i).getUserId(), user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Booking booking = new Booking();
            booking.setShowId(reservedSeats.get(i).getShowId());
            booking.setSeatReservationId(reservedSeats.get(i).getSeatId());
            booking.setUserId(reservedSeats.get(i).getUserId());
            booking.setBookingCost(showRepository.findByShowId(reservedSeats.get(i).getShowId()).getShowCost());
            booking.setBookingDate(showRepository.findByShowId(reservedSeats.get(i).getShowId()).getShowDate());
            bookings.add(bookingRepository.save(booking));

            SeatReservation seatReservation = seatReservationRepository.findBySeatId(reservedSeats.get(i).getSeatId());
            seatReservation.setBookingId(bookings.get(i).getBookingId());
            seatReservation.setSeatPaid(true);
            seatReservationRepository.save(seatReservation);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // TODO: add user check
    @Override
    @Transactional
    public ResponseEntity<?> cancelSingleTicket(Integer bookingId) {

        Booking booking = bookingRepository.findByBookingId(bookingId);
        SeatReservation seatReservation = seatReservationRepository.findByBookingId(bookingId);
        List<Booking> bookingList = bookingRepository.findAllByUserId(booking.getUserId());
        bookingRepository.delete(booking);
        bookingList.remove(booking);
        seatReservation.setUserId(null);
        seatReservation.setBookingId(null);
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
    public ResponseEntity<?> getSingleBooking(Authentication authentication, Integer bookingId) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(bookingRepository.findByBookingId(bookingId).getUserId(), user)) {
            return new ResponseEntity<>(bookingRepository.findByBookingId(bookingId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSingleBooking(Authentication authentication, Integer bookingId, Booking newBooking) {
        User user = userRepository.findByEmail((authentication.getName()));
        Booking booking = bookingRepository.getOne(bookingId);
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(booking.getUserId(), user)) {
            booking.setUserId(newBooking.getUserId());
            booking.setBookingDate(newBooking.getBookingDate());
            booking.setShowId(newBooking.getShowId());
            return new ResponseEntity<>(bookingRepository.save(booking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteSingleBooking(Authentication authentication, Integer bookingId) {
        User user = userRepository.findByEmail((authentication.getName()));
        Booking booking = bookingRepository.getOne(bookingId);
        if (customAccessHandler.userIsAdmin(user)) {
            bookingRepository.delete(booking);
            return new ResponseEntity<>(bookingRepository.findAllByUserId(user.getUserId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
