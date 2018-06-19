package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "seat")
@EntityListeners(AuditingEntityListener.class)
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue
    private Integer seat_id;

    @Column(name = "show_id")
    private Integer show_id;

    @Column(name = "seat_status")
    private boolean seat_status;

    @Column(name = "booking_id")
    private Integer booking_id;

    @Column(name = "theatre_id")
    private  Integer theatre_id;

    public Seat() {
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public boolean isSeat_status() {
        return seat_status;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public Integer getTheatre_id() {
        return theatre_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public void setSeat_status(boolean seat_status) {
        this.seat_status = seat_status;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public void setTheatre_id(Integer theatre_id) {
        this.theatre_id = theatre_id;
    }
}
