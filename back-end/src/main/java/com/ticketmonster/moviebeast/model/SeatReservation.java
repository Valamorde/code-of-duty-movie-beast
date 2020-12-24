package com.ticketmonster.moviebeast.model;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_generator")
    @SequenceGenerator(name = "seat_generator", sequenceName = "seat_seq")
    private Long id;

    @Column(name = "reserved")
    private boolean reserved;

    @Column(name = "paid")
    private boolean paid;

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

    public Long getId() {
        return id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean seatReserved) {
        this.reserved = seatReserved;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean seatPaid) {
        this.paid = seatPaid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SeatReservation{");

        sb.append("id=").append(id);
        sb.append(", reserved=").append(reserved);
        sb.append(", paid=").append(paid);
        sb.append(", user=").append(user);
        sb.append(", booking=").append(booking);
        sb.append(", show=").append(show);
        sb.append('}');

        return sb.toString();
    }
}
