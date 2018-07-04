package com.ticketmonster.movie_beast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "theatres")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class Theatre implements Serializable {

    @Id
    @Column(name = "theatreId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theatre_generator")
    @SequenceGenerator(name = "theatre_generator", sequenceName = "theatre_seq")
    private Integer theatreId;

    @Column(name = "cityId")
    private Integer cityId;

    @Column(name = "theatreName")
    private String theatreName;

    @Column(name = "theatreAddress")
    private String theatreAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "theatreId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Movie> movies;

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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
