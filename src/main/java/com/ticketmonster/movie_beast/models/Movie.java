package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
public class Movie implements Serializable {

    @Id
    @Column(name = "movieId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_generator")
    @SequenceGenerator(name="movie_generator", sequenceName = "movie_seq")
    private Integer movieId;

    @Column(name = "movieName")
    private String movieName;

    @Column(name = "movieDescription")
    private String movieDescription;

    public Movie() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }
}
