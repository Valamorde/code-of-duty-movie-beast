package com.ticketmonster.events;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String title;
    @Column
    private String location;
    @Column
    private BigDecimal price;
    @Column
    private byte sold_out;
    @Column
    private int event_duration;
    @Column
    private Date event_time;

    public int getEvent_duration() {
        return event_duration;
    }

    public void setEvent_duration(int event_duration) {
        this.event_duration = event_duration;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte getSold_out() {
        return sold_out;
    }

    public void setSold_out(byte sold_out) {
        this.sold_out = sold_out;
    }
}
