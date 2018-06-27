package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "shows")
@EntityListeners(AuditingEntityListener.class)
public class Show implements Serializable {

    @Id
    @Column(name = "showId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "show_generator")
    @SequenceGenerator(name="show_generator", sequenceName = "show_seq")
    private Integer showId;

    @Column(name = "theatreId")
    private Integer theatreId;

    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "showDate")
    @Temporal(TemporalType.DATE)
    private Date showDate;

    @Column(name = "showDurationInMinutes")
    private Integer showDurationInMinutes;

    @Column(name = "initialSeats")
    private Integer initialSeats;

    @Column(name = "availableSeats")
    private Integer availableSeats;

    @Column(name = "showCost")
    private BigDecimal showCost;

    public Show() {
    }

    public Integer getShowId() {
        return showId;
    }

    public Integer getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Integer theatreId) {
        this.theatreId = theatreId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Integer getShowDurationInMinutes() {
        return showDurationInMinutes;
    }

    public void setShowDurationInMinutes(Integer showDurationInMinutes) {
        this.showDurationInMinutes = showDurationInMinutes;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getShowCost() {
        return showCost;
    }

    public void setShowCost(BigDecimal showCost) {
        this.showCost = showCost;
    }

    public Integer getInitialSeats() {
        return initialSeats;
    }

    public void setInitialSeats(Integer initialSeats) {
        this.initialSeats = initialSeats;
    }


}
