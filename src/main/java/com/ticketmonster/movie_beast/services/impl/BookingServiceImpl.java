package com.ticketmonster.movie_beast.services.impl;

import com.ticketmonster.movie_beast.helpers.config.CustomAccessHandler;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.BookingRepository;
import com.ticketmonster.movie_beast.repositories.SeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.ShowRepository;
import com.ticketmonster.movie_beast.repositories.UserRepository;
import com.ticketmonster.movie_beast.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SeatReservationRepository seatReservationRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomAccessHandler customAccessHandler;

    @Transactional
    public ResponseEntity<?> bookTickets(Authentication authentication) {

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(authentication.getName());
        List<SeatReservation> reservedSeats = seatReservationRepository.findAllBySeatReservedIsTrueAndSeatPaidIsFalseAndUserIdIs(user.getUserId());
        List<Booking> bookings = new ArrayList<>();

        for (int i = 0; i < reservedSeats.size(); i++) {
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
    public ResponseEntity<?> findAllBookings(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAdmin(user)) {
            return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<?> findSingleBooking(Authentication authentication, Integer bookingId) {
        User user = userRepository.findByEmail(authentication.getName());
        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(bookingRepository.findByBookingId(bookingId).getUserId(), user)) {
            return new ResponseEntity<>(bookingRepository.findByBookingId(bookingId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
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
