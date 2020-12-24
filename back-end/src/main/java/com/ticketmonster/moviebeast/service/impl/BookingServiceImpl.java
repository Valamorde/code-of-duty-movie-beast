package com.ticketmonster.moviebeast.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.SeatReservation;
import com.ticketmonster.moviebeast.model.User;
import com.ticketmonster.moviebeast.repository.BookingRepository;
import com.ticketmonster.moviebeast.repository.SeatReservationRepository;
import com.ticketmonster.moviebeast.repository.UserRepository;
import com.ticketmonster.moviebeast.service.BookingService;
import com.ticketmonster.moviebeast.util.handlers.CustomAccessHandler;
import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatReservationRepository seatReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomAccessHandler customAccessHandler;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private DataSource dataSource;

    @Override
    @Transactional
    public void bookAllInBasket(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        List<SeatReservation> reservedSeats = seatReservationRepository.findAllByReservedIsTrueAndPaidIsFalseAndUserIs(user);
        List<Booking> bookings = Lists.newArrayList();

        for (int i = 0; i < reservedSeats.size(); i++) {
            Booking booking = new Booking();
            booking.setSeatReservation(reservedSeats.get(i));
            booking.setUser(reservedSeats.get(i).getUser());
            booking.setCost(booking.getSeatReservation().getShow().getCost());
            booking.setDate(booking.getSeatReservation().getShow().getDate());
            bookings.add(bookingRepository.save(booking));

            SeatReservation seatReservation = seatReservationRepository.getOne(reservedSeats.get(i).getId());
            seatReservation.setBooking(bookings.get(i));
            seatReservation.setPaid(true);
            seatReservationRepository.save(seatReservation);
        }

        logger.info("User:[" + user.getId() + "] booked [" + bookings.size() + "] tickets.");
    }

    @Override
    @Transactional
    public void cancelSingleTicket(Authentication authentication, Booking booking) {
        User user = userRepository.findByEmail(authentication.getName());
        SeatReservation seatReservation = seatReservationRepository.findByBooking(booking);

        List<Booking> bookingList = bookingRepository.findAllByUser(booking.getUser());
        bookingList.remove(booking);
        seatReservation.setUser(null);
        seatReservation.setBooking(null);
        seatReservation.setPaid(false);
        seatReservation.setReserved(false);
        seatReservationRepository.save(seatReservation);
        bookingRepository.delete(booking);

        logger.info("User:[" + user.getId() + "] cancelled Booking:[" + booking.getId() + "].");
    }

    @Override
    @Transactional
    public List<Booking> getAllBookings(Authentication authentication) {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional
    public Booking getSingleBooking(Authentication authentication, Booking booking) {
        return bookingRepository.getOne(booking.getId());
    }

    @Override
    @Transactional
    public void updateSingleBooking(Authentication authentication, Booking booking, Booking newBooking) {
        booking.setUser(newBooking.getUser());
        booking.setDate(newBooking.getDate());
        logger.info("Updated Booking with ID:[" + booking.getId() + "].");
    }

    @Override
    @Transactional
    public void deleteSingleBooking(Authentication authentication, Booking booking) {
        bookingRepository.delete(booking);
        logger.info("Deleted Booking with ID:[" + booking.getId() + "].");
    }

    @Override
    public void printTickets(User user, HttpServletResponse res, HttpServletRequest req) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String jrxml = "userBookedTickets";
            Resource resource = context.getResource("classpath:/static/" + jrxml + ".jrxml");

            InputStream inputStream = resource.getInputStream();

            JasperReport report = JasperCompileManager.compileReport(inputStream);

            Map params = Maps.newHashMap();
            params.put("id", user.getId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);

            res.setContentType(MediaType.APPLICATION_PDF_VALUE);

            res.setHeader("Content-Disposition", "filename=\"tickets" + ".pdf\"");
            JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
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

            Map params = Maps.newHashMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
