package com.ticketmonster.movie_beast.rest_controllers;

import com.ticketmonster.movie_beast.custom_exceptions.CustomException;
import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.models.User;
import com.ticketmonster.movie_beast.repositories.BookingRepository;
import com.ticketmonster.movie_beast.repositories.ShowRepository;
import com.ticketmonster.movie_beast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class BookingCreationController {

    @Autowired
    private BookingRepository bookingRepository;

 @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket()/*(@RequestBody Integer event_id, @RequestBody String seat, @RequestBody Integer numberOfTickets) */ {

        int show_id = 2;
        int numberOfTickets = 2;
        int seat = 1;
        Show show = showRepository.getOne(show_id);

        if (numberOfTickets <= show.getAvailable_seats()) {
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //User user = userRepository.findByEmail(authentication.getPrincipal().toString());
            User user = userRepository.findByEmail("admin@dummy.com");

            List<Booking> bookings = new ArrayList<Booking>();

            for (int i = 0; i < numberOfTickets; i++) {
                Booking booking = new Booking();
                booking.setShow_id(show.getShow_id());
                booking.setUser_id(user.getUser_id());
                booking.setBooking_cost(show.getShow_cost());
                booking.setSeat_id(seat);
                bookings.add(bookingRepository.save(booking));
            }
            show.setAvailable_seats(show.getAvailable_seats() - numberOfTickets);
            showRepository.save(show);

            return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new CustomException("Sorry but it seems we don't have that many tickets available."), HttpStatus.RESET_CONTENT);
        }
    }

    //    @GetMapping(path = "/{event_id}/printTicket.pdf", produces = "application/pdf") // FIXME:
    //    public ResponseEntity<?> printTicket() {
    //        PDDocument pdf = new PDDocument();
    //
    //        pdf.addPage(new PDPage());
    //        PDStream pdStream = new PDStream(pdf);
    //
    //        try {
    //            COSInputStream cosInputStream = pdStream.createInputStream();
    //
    //            return ResponseEntity.ok().contentLength(pdStream.getLength())
    //                    .contentType(MediaType.parseMediaType("application/octet-stream"))
    //                    .body(new InputStreamResource(cosInputStream));
    //        } catch (IOException e) {
    //            return ResponseEntity.badRequest().build();
    //        }
    //    }
}
