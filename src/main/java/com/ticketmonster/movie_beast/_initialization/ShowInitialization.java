package com.ticketmonster.movie_beast._initialization;

import com.ticketmonster.movie_beast.models.Show;
import com.ticketmonster.movie_beast.repositories.IShowRepository;
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
    IShowRepository showRepository;

    @PostConstruct
    @Transactional
    public void init() {
        IntStream.range(0, 10).forEach((i -> {
            int random = (int) (Math.random() * 5 + 1);
            random = (random == 0 || random == 6) ? 1 : random;
            Show show = new Show();
            show.setShow_duration_in_minutes((i + 1) * 10);
            show.setShow_cost(new BigDecimal(7, MathContext.DECIMAL64));
            show.setTheatre_id(i + 1);
            show.setMovie_id(random);
            show.setShow_date(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            show.setAvailable_seats(10);
            showRepository.save(show);
        }));
    }
}
