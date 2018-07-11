package com.ticketmonster.moviebeast.services.implementations;

import com.ticketmonster.moviebeast.helpers.handlers.CustomAccessHandler;
import com.ticketmonster.moviebeast.models.Booking;
import com.ticketmonster.moviebeast.models.SeatReservation;
import com.ticketmonster.moviebeast.models.User;
import com.ticketmonster.moviebeast.repositories.IBookingRepository;
import com.ticketmonster.moviebeast.repositories.ISeatReservationRepository;
import com.ticketmonster.moviebeast.repositories.IUserRepository;
import com.ticketmonster.moviebeast.services._interfaces.IBookingService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class BookingServiceImpl is handling all the transactions for the bookings.
 * Namely, it handles bookings, cancellations, updates for signle booking, deletions and printing of tickets booked.
 */
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

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataSource dataSource;

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
            if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(reservedSeats.get(i).getUser(), user)) {
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
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> cancelSingleTicket(Authentication authentication, Booking booking) {

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByEmail(authentication.getName());
        SeatReservation seatReservation = seatReservationRepository.findByBooking(booking);

        if (customAccessHandler.userIsAuthorizedToViewSpecifiedContent(booking.getUser(), user)) {
            List<Booking> bookingList = bookingRepository.findAllByUser(booking.getUser());
            bookingRepository.delete(booking);
            bookingList.remove(booking);
            seatReservation.setUser(null);
            seatReservation.setBooking(null);
            seatReservation.setSeatPaid(false);
            seatReservation.setSeatReserved(false);
            seatReservationRepository.save(seatReservation);

            return new ResponseEntity<>(bookingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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

    @Override
    public void printTickets(User user, HttpServletResponse res, HttpServletRequest req) {
        try {
            Connection conn = dataSource.getConnection();

            String jrxml = "userBookedTickets";
            Resource resource = context.getResource("classpath:/static/" + jrxml + ".jrxml");

            InputStream inputStream = resource.getInputStream();

            JasperReport report = JasperCompileManager.compileReport(inputStream);

            HashMap params = new HashMap();
            params.put("id", user.getUserId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);

            res.setContentType(MediaType.APPLICATION_PDF_VALUE);

            res.setHeader("Content-Disposition", "filename=\"tickets" + ".pdf\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printTicketReport(User user, HttpServletResponse res, HttpServletRequest req) {
        try {
            Connection conn = dataSource.getConnection();

            String jrxml = "adminTicketReport";
            Resource resource = context.getResource("classpath:/static/" + jrxml + ".jrxml");

            InputStream inputStream = resource.getInputStream();

            JasperReport report = JasperCompileManager.compileReport(inputStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, conn);

            res.setContentType(MediaType.APPLICATION_PDF_VALUE);

            res.setHeader("Content-Disposition", "filename=\"tickets" + ".pdf\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());

            HashMap params = new HashMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
