package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking")
@EntityListeners(AuditingEntityListener.class)
public class Booking implements Serializable {

    @Id
    @Column(name = "booking_id")
    @GeneratedValue
    private Integer booking_id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "booking_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date booking_date;

    @Column(name = "show_id")
    private Integer show_id;

    @Column(name = "booking_cost")
    private BigDecimal booking_cost;

    @Column(name = "seat_id")
    private Integer seat_id;

    public Booking() {
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public BigDecimal getBooking_cost() {
        return booking_cost;
    }

    public void setBooking_cost(BigDecimal booking_cost) {
        this.booking_cost = booking_cost;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }
}
