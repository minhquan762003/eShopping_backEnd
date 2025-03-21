package com.example.E_Shopping.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String password;
    private String email;
    private String name;
    private Date birthDay;
    private String number;
    private int gender;
    private Date createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

}
