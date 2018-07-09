package com.ticketmonster.moviebeast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat_reservations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class SeatReservation implements Serializable {

    @Id
    @Column(name = "seatId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name = "seat_generator", sequenceName = "seat_seq")
    private Integer seatId;

    @Column(name = "seatReserved")
    private boolean seatReserved;

    @Column(name = "seatPaid")
    private boolean seatPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "showId")
    private Show show;

    public SeatReservation() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public boolean isSeatReserved() {
        return seatReserved;
    }

    public void setSeatReserved(boolean seatReserved) {
        this.seatReserved = seatReserved;
    }

    public boolean isSeatPaid() {
        return seatPaid;
    }

    public void setSeatPaid(boolean seatPaid) {
        this.seatPaid = seatPaid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
