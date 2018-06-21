package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat")
@EntityListeners(AuditingEntityListener.class)
public class Seat implements Serializable {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name = "seat_generator", sequenceName = "seat_seq")
    private Integer seat_id;

    @Column(name = "show_id")
    private Integer show_id;

    @Column(name = "seat_reserved")
    private boolean seat_reserved;

    @Column(name = "booking_id", nullable = true)
    private Integer booking_id;

    @Column(name = "theatre_id")
    private Integer theatre_id;

    @Column(name = "paid")
    private boolean paid;

    public Seat() {
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public boolean isSeat_reserved() {
        return seat_reserved;
    }

    public void setSeat_reserved(boolean seat_reserved) {
        this.seat_reserved = seat_reserved;
    }

    public Integer getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(Integer booking_id) {
        this.booking_id = booking_id;
    }

    public Integer getTheatre_id() {
        return theatre_id;
    }

    public void setTheatre_id(Integer theatre_id) {
        this.theatre_id = theatre_id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
