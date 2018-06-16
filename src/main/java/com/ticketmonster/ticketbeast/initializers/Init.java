package com.ticketmonster.ticketbeast.initializers;

import com.ticketmonster.ticketbeast.models.Event;
import com.ticketmonster.ticketbeast.models.Role;
import com.ticketmonster.ticketbeast.models.User;
import com.ticketmonster.ticketbeast.repositories.EventRepository;
import com.ticketmonster.ticketbeast.repositories.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

@Component
public class Init {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 20).forEach((i -> {
            Event event = new Event();
            event.setEvent_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            event.setAvailable_tickets(100);
            event.setCreated_at(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            event.setDuration(2);
            event.setPrice(new BigDecimal(50, MathContext.DECIMAL64));
            event.setLocation("Dummy Location");
            event.setName("Dummy Event");
            eventRepository.save(event);
        }));

        User user = new User();
        user.setEmail("user@dummy.com");
        user.setFirst_name("Dummy");
        user.setLast_name("DummyDum");
        user.setPassword("12345");
        user.setRole(Role.USER);
        userRepository.save(user);

        user = new User();
        user.setEmail("admin@dummy.com");
        user.setFirst_name("ADummy");
        user.setLast_name("ADummyDum");
        user.setPassword("12345");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
