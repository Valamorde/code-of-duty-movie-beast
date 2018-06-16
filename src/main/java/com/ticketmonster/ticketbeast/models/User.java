package com.ticketmonster.ticketbeast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_at"}, allowGetters = true, allowSetters = true)
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long user_id;

    @Column(name = "last_name")
    @NotBlank
    private String last_name;

    @Column(name = "first_name")
    @NotBlank
    private String first_name;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    public User() {
    }

    public User(@NotBlank String last_name, @NotBlank String first_name, Date created_at) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.created_at = created_at;
    }

    public Account getAccount() {
        return account;
    }

//    public void setAccount(Account account) {
//        this.account = account;
//    }

    public long getUser_id() {
        return user_id;
    }

    public String getLast_name() {

        return last_name;
    }

    public void setLast_name(String last_name) {

        this.last_name = last_name;
    }

    public String getFirst_name() {

        return first_name;
    }

    public void setFirst_name(String first_name) {

        this.first_name = first_name;
    }

    public Date getCreated_at() {

        return created_at;
    }
}
