package com.ticketmonster.moviebeast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shows")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Show implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "show_generator")
    @SequenceGenerator(name = "show_generator", sequenceName = "show_seq")
    private Long id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "initial_seats")
    private Integer initialSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @Column(name = "show_cost")
    private BigDecimal cost;

    @JsonIgnore
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatReservation> seats;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieId")
    private Movie movie;

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

    public Integer getInitialSeats() {
        return initialSeats;
    }

    public void setInitialSeats(Integer initialSeats) {
        this.initialSeats = initialSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<SeatReservation> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatReservation> seats) {
        this.seats = seats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Show{");

        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", initialSeats=").append(initialSeats);
        sb.append(", availableSeats=").append(availableSeats);
        sb.append(", cost=").append(cost);
        sb.append(", seats=").append(seats);
        sb.append(", movie=").append(movie);
        sb.append('}');

        return sb.toString();
    }
}
