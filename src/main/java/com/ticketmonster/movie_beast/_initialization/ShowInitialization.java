package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.repositories.ShowRepository;
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
public class ShowInitialization {

    @Autowired
    ShowRepository showRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 20).forEach((i -> {
            Show show = new Show();
            show.setShow_duration_in_minutes((i+1)*10);
            show.setShow_cost(new BigDecimal(7, MathContext.DECIMAL64));
            show.setTheatre_id(i+1);
            show.setMovie_id((i+1 < 5)?i+1:4);
            show.setShow_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            show.setAvailable_seats(50);
            showRepository.save(show);
        }));
    }
}
