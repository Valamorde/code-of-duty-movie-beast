package com.ticketmonster.movie_beast.event_handlers;

import com.ticketmonster.movie_beast.models.Booking;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.data.rest.core.annotation.*;

@Component
@RepositoryEventHandler(Booking.class)
public class BookingEventHandler {

    private final static Log logger = LogFactory.getLog(BookingEventHandler.class);

    @HandleBeforeSave
    public void handleBookingBeforeSave(Booking booking) {
        logger.info("Booking before save " + booking);
    }

    @HandleAfterSave
    public void handleBookingAfterSave(Booking booking) {
        logger.info("Booking after save " + booking);
    }
}
