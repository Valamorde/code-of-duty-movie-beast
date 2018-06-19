package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@EntityListeners(AuditingEntityListener.class)
public class Movie {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue
    private Integer movie_id;

    @Column(name = "movie_name")
    private String movie_name;

    @Column(name = "movie_description")
    private String movie_description;

    public Movie() {
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }
}
