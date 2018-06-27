package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cities")
@EntityListeners(AuditingEntityListener.class)
public class City implements Serializable {

    @Id
    @Column(name = "cityId")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_generator")
    @SequenceGenerator(name="city_generator", sequenceName = "city_seq")
    private Integer cityId;

    @Column(name = "cityName")
    private String cityName;

    public City() {
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
