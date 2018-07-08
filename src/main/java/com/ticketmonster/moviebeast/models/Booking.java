package com.ticketmonster.moviebeast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bookings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Booking implements Serializable {

    @Id
    @Column(name = "bookingId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator", sequenceName = "booking_seq")
    private Integer bookingId;

    @Column(name = "bookingDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "bookingCost")
    private BigDecimal bookingCost;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SeatReservation seatReservation;

    public Booking() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(BigDecimal bookingCost) {
        this.bookingCost = bookingCost;
    }

    public SeatReservation getSeatReservation() {
        return seatReservation;
    }

    public void setSeatReservation(SeatReservation seatReservation) {
        this.seatReservation = seatReservation;
    }
}
