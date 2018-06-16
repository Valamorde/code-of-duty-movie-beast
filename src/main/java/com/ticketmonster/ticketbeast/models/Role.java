package com.ticketmonster.ticketbeast.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role implements Serializable {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long role_id;

    @Column(name = "title")
    @NotBlank
    private String title;

    public Role() {
    }

    public Role(@NotBlank String title) {
        this.title = title;
    }

    public long getRole_id() {
        return role_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
