package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "theatre")
@EntityListeners(AuditingEntityListener.class)
public class Theatre {

    @Id
    @Column(name = "theatre_id")
    @GeneratedValue
    private Integer theatre_id;

    @Column(name = "city_id")
    private Integer city_id;

    @Column(name = "movie_id")
    private Integer movie_id;

    @Column(name = "movie_release_date")
    @Temporal(TemporalType.DATE)
    private Date movie_release_date;

    @Column(name = "theatre_name")
    private String theatre_name;

    @Column(name = "theatre_address")
    private String theatre_address;

    public Theatre() {
    }

    public Integer getTheatre_id() {
        return theatre_id;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Date getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(Date movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public String getTheatre_name() {
        return theatre_name;
    }

    public void setTheatre_name(String theatre_name) {
        this.theatre_name = theatre_name;
    }

    public String getTheatre_address() {
        return theatre_address;
    }

    public void setTheatre_address(String theatre_address) {
        this.theatre_address = theatre_address;
    }
}
