package com.ticketmonster.deprecated_logic_for_reference.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "event_tickets")
@EntityListeners(AuditingEntityListener.class)
public class EventTicket implements Serializable {

    @Id
    @Column(name = "event_ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer event_ticket_id;

    @Column(name = "event_id")
    private Integer event_id;

    @Column(name = "seat")
    private String seat;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "user_id")
    private Integer user;

    public EventTicket() {
    }

    public Integer getEvent_ticket_id() {
        return event_ticket_id;
    }

    public void setEvent_ticket_id(Integer event_ticket_id) {
        this.event_ticket_id = event_ticket_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
