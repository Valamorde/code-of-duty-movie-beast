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
    @Column(name = "show_id")
    @GeneratedValue
    private Integer show_id;

    @Column(name = "theatre_id")
    private Integer theatre_id;

    @Column(name = "movie_id")
    private Integer movie_id;

    @Column(name = "show_date")
    @Temporal(TemporalType.DATE)
    private Date show_date;

    @Column(name = "show_duration_in_minutes")
    private Integer show_duration_in_minutes;

    @Column(name = "available_seats")
    private Integer available_seats;

    @Column(name = "show_cost")
    private BigDecimal show_cost;

    public Show() {
    }

    public Integer getShow_id() {
        return show_id;
    }

    public Integer getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(Integer theatre_id) {
        this.theatre_id = theatre_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Date getShow_date() {
        return show_date;
    }

    public void setShow_date(Date show_date) {
        this.show_date = show_date;
    }

    public Integer getShow_duration_in_minutes() {
        return show_duration_in_minutes;
    }

    public void setShow_duration_in_minutes(Integer show_duration_in_minutes) {
        this.show_duration_in_minutes = show_duration_in_minutes;
    }

    public Integer getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(Integer available_seats) {
        this.available_seats = available_seats;
    }

    public BigDecimal getShow_cost() {
        return show_cost;
    }

    public void setShow_cost(BigDecimal show_cost) {
        this.show_cost = show_cost;
    }
}
