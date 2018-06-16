package com.ticketmonster.ticketbeast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_at"}, allowGetters = true, allowSetters = true)
public class Account implements Serializable {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long account_id;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "password")
    @NotBlank
    private String password;

    @Column(name = "role_id")
    @NotBlank
    private int role_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    public Account() {
    }

    public Account(@NotBlank String email, @NotBlank String password, @NotBlank int role_id, Date created_at) {
        this.email = email;
        this.password = password;
        this.role_id = role_id;
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public long getAccount_id() {
        return account_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
