package com.ticketmonster.movie_beast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "city")
@EntityListeners(AuditingEntityListener.class)
public class City implements Serializable {

    @Id
    @Column(name = "city_id")
    @GeneratedValue
    private Integer city_id;

    @Column(name = "city_name")
    private String city_name;

    public City() {
    }

    public Integer getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
