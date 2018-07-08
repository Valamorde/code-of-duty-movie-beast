package com.ticketmonster.moviebeast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shows")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Show implements Serializable {

    @Id
    @Column(name = "showId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "show_generator")
    @SequenceGenerator(name = "show_generator", sequenceName = "show_seq")
    private Integer showId;

    @Column(name = "showDate")
    @Temporal(TemporalType.DATE)
    private Date showDate;

    @Column(name = "initialSeats")
    private Integer initialSeats;

    @Column(name = "availableSeats")
    private Integer availableSeats;

    @Column(name = "showCost")
    private BigDecimal showCost;

    @JsonIgnore
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatReservation> seats;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movieId")
    private Movie movie;

    public Show() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getShowId() {
        return showId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
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

    public List<SeatReservation> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatReservation> seats) {
        this.seats = seats;
    }
}
