package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "theatres")
@EntityListeners(AuditingEntityListener.class)
public class Theatre implements Serializable {

    @Id
    @Column(name = "theatreId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theatre_generator")
    @SequenceGenerator(name = "theatre_generator", sequenceName = "theatre_seq")
    private Integer theatreId;

    @Column(name = "cityId")
    private Integer cityId;

    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "theatreName")
    private String theatreName;

    @Column(name = "theatreAddress")
    private String theatreAddress;

    public Theatre() {
    }

    public Integer getTheatreId() {
        return theatreId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreAddress() {
        return theatreAddress;
    }

    public void setTheatreAddress(String theatreAddress) {
        this.theatreAddress = theatreAddress;
    }
}
