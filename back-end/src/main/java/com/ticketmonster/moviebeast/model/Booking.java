package com.ticketmonster.moviebeast.model;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator", sequenceName = "booking_seq")
    private Long id;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "cost")
    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SeatReservation seatReservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SeatReservation getSeatReservation() {
        return seatReservation;
    }

    public void setSeatReservation(SeatReservation seatReservation) {
        this.seatReservation = seatReservation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Booking{");

        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", cost=").append(cost);
        sb.append(", user=").append(user);
        sb.append(", seatReservation=").append(seatReservation);
        sb.append('}');

        return sb.toString();
    }
}
