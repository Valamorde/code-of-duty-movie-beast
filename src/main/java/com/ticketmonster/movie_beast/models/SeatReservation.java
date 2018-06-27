package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seat_reservations")
@EntityListeners(AuditingEntityListener.class)
public class SeatReservation implements Serializable {

    @Id
    @Column(name = "seatId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name = "seat_generator", sequenceName = "seat_seq")
    private Integer seatId;

    @Column(name = "showId")
    private Integer showId;

    @Column(name = "seatReserved")
    private boolean seatReserved;

    @Column(name = "bookingId", nullable = true)
    private Integer bookingId;

    @Column(name = "theatreId")
    private Integer theatreId;

    @Column(name = "seatPaid")
    private boolean seatPaid;

    @Column(name = "userId")
    private Integer userId;

    public SeatReservation() {
    }

    public Integer getSeatId() {
        return seatId;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public boolean isSeatReserved() {
        return seatReserved;
    }

    public void setSeatReserved(boolean seatReserved) {
        this.seatReserved = seatReserved;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Integer theatreId) {
        this.theatreId = theatreId;
    }

    public boolean isSeatPaid() {
        return seatPaid;
    }

    public void setSeatPaid(boolean seatPaid) {
        this.seatPaid = seatPaid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
