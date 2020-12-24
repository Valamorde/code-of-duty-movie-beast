package com.ticketmonster.moviebeast.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cities")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class City implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_generator")
    @SequenceGenerator(name = "city_generator", sequenceName = "city_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Theatre> theatres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Theatre> getTheatres() {
        return theatres;
    }

    public void setTheatres(List<Theatre> theatres) {
        this.theatres = theatres;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("City{");

        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", theatres=").append(theatres);
        sb.append('}');

        return sb.toString();
    }
}
