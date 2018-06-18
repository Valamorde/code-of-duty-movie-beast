package com.ticketmonster.ticketbeast.initializers;

import com.ticketmonster.ticketbeast.models.Event;
import com.ticketmonster.ticketbeast.models.Role;
import com.ticketmonster.ticketbeast.models.User;
import com.ticketmonster.ticketbeast.repositories.EventRepository;
import com.ticketmonster.ticketbeast.repositories.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 10).forEach((i -> {
            Event event = new Event();
            event.setEvent_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            event.setAvailable_tickets(100*i/2);
            event.setCreated_at(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            event.setDuration(120);
            event.setPrice(new BigDecimal(10*i, MathContext.DECIMAL64));
            event.setLocation("Dummy Location");
            event.setName("Dummy Event");
            eventRepository.save(event);
        }));

        User user = new User();
        user.setEmail("user@dummy.com");
        user.setFirst_name("Dummy");
        user.setLast_name("DummyDum");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);

        user = new User();
        user.setEmail("admin@dummy.com");
        user.setFirst_name("ADummy");
        user.setLast_name("ADummyDum");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setRole("ADMIN");
        user.setEnabled(true);
        userRepository.save(user);
    }
}
