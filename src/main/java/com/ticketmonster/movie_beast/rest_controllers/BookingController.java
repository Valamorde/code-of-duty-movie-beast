package com.ticketmonster.movie_beast.rest_controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.services.impl.BookingServiceImpl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Component
@RestController
public class BookingController {

	@Autowired
    ApplicationContext context;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	private BookingServiceImpl bookingService;

	// Book Reserved Seats
	@PostMapping("/bookings/final")
	@Produces("application/json")
	public ResponseEntity<?> finalizeBookings() {
		try {
			return bookingService.bookTickets(SecurityContextHolder.getContext().getAuthentication());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cancel a Booked Ticket
	@PostMapping("/bookings/cancel")
	@Consumes("application/json")
	public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking) {
		try {
			return bookingService.cancelSingleTicket(booking.getBookingId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Get All Bookings
	@GetMapping("/bookings")
	@Produces("application/json")
	public ResponseEntity<?> getAllBookings() {
		try {
			return bookingService.findAllBookings(SecurityContextHolder.getContext().getAuthentication());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Get a Single Booking
	@GetMapping("/bookings/{id}")
	@Produces("application/json")
	public ResponseEntity<?> getBookingById(@PathVariable(value = "id") Integer id) {
		try {
			return bookingService.findSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Update a Booking
	@PutMapping("/bookings/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<?> updateBooking(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody Booking bookingDetails) {
		try {
			return bookingService.updateSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id,
					bookingDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// Allows Admin to delete a Booking
	@DeleteMapping("/bookings/{id}")
	@Produces("application/json")
	public ResponseEntity<?> deleteBooking(@PathVariable(value = "id") Integer id) {
		try {
			return bookingService.deleteSingleBooking(SecurityContextHolder.getContext().getAuthentication(), id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// print reserved ticket
	@GetMapping(path = "/bookings/pdf/{user_id}")
	public void printTicket(HttpServletResponse response, @PathVariable("user_id") String userid,
			HttpServletRequest httpServletRequest) {

		try {
			Connection conn = dataSource.getConnection();
			
			String jrxml = "ticketreport";
			Resource resource = context.getResource("classpath:/" + jrxml + ".jrxml");
	
			InputStream inputStream = resource.getInputStream();
			
			JasperReport report = JasperCompileManager.compileReport(inputStream);
			
			HashMap params = new HashMap();
			params.put("id", userid);
	
			// Make jasperPrint
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);
		
			response.setContentType(MediaType.APPLICATION_PDF_VALUE);
			
			response.setHeader("Content-Disposition", "filename=\"ticket" + ".pdf\"");
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			
		} catch (IOException | SQLException | JRException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}
}
