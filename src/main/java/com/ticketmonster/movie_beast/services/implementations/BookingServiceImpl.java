package com.ticketmonster.movie_beast.services.implementations;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.SeatReservation;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.IBookingRepository;
import com.ticketmonster.movie_beast.repositories.ISeatReservationRepository;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
import com.ticketmonster.movie_beast.services._interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public List<Booking> bookTicket(User user) {

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

        return bookings;
    }

    //TODO: enable user to cancel their own tickets
    @Transactional
    public Booking cancelTicket(Integer bookingId){

        Booking booking = bookingRepository.findByBookingId(bookingId);
        SeatReservation seatReservation = seatReservationRepository.findByBookingId(bookingId);

        bookingRepository.delete(booking);
        seatReservation.setUserId(null);
        seatReservation.setBookingId(null);
        seatReservation.setSeatPaid(false);
        seatReservation.setSeatReserved(false);
        seatReservationRepository.save(seatReservation);

        return null;
    }
}
