package com.ticketmonster.ticketbeast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_at"}, allowGetters = true, allowSetters = true)
public class Event implements Serializable {

    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long event_id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "location")
    @NotBlank
    private String location;

    @Column(name = "duration")
    @NotBlank
    private int duration;

    @Column(name = "price")
    @NotBlank
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

    public Event(@NotBlank String name, @NotBlank String location, @NotBlank int duration, @NotBlank BigDecimal price, Date event_date, Date created_at) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.price = price;
        this.event_date = event_date;
        this.created_at = created_at;
    }

    public long getEvent_id() {
        return event_id;
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
}
