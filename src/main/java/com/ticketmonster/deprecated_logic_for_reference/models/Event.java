package com.ticketmonster.deprecated_logic_for_reference.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
public class Event implements Serializable {

    @Id
    @Column(name = "event_id", precision = 10)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer event_id;

    @Column(name = "name", precision = 140)
    private String name;

    @Column(name = "location", precision = 140)
    private String location;

    @Column(name = "duration", precision = 5)
    private int duration;

    @Column(name = "available_tickets")
    private int available_tickets;

    @Column(name = "price", precision = 5)
    private BigDecimal price;

    @Column(name = "event_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date event_date;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    public Event() {
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAvailable_tickets() {
        return available_tickets;
    }

    public void setAvailable_tickets(int available_tickets) {
        this.available_tickets = available_tickets;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
