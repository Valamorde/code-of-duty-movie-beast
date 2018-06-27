package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bookings")
@EntityListeners(AuditingEntityListener.class)
public class Booking implements Serializable {

    @Id
    @Column(name = "bookingId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator", sequenceName = "booking_seq")
    private Integer bookingId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "bookingDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "showId")
    private Integer showId;

    @Column(name = "bookingCost")
    private BigDecimal bookingCost;

    @Column(name = "seatReservationId")
    private Integer seatReservationId;

    public Booking() {
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public BigDecimal getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(BigDecimal bookingCost) {
        this.bookingCost = bookingCost;
    }

    public Integer getSeatReservationId() {
        return seatReservationId;
    }

    public void setSeatReservationId(Integer seatReservationId) {
        this.seatReservationId = seatReservationId;
    }
}
