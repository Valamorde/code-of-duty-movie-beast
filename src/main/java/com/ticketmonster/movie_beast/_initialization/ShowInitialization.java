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
            show.setShowDurationInMinutes((i + 1) * 10);
            show.setShowCost(new BigDecimal(3.5, MathContext.DECIMAL64));
            show.setTheatreId(i + 1);
            show.setMovieId(random);
            show.setShowDate(DateUtils.round(DateUtils.addDays(new Date(), i / 2), Calendar.DATE));
            show.setAvailableSeats(10);
            show.setInitialSeats(10);
            showRepository.save(show);
        }));
    }
}
