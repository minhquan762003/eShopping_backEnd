package com.example.E_Shopping.model;

import java.util.Date;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; 

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    // Phương thức đảm bảo `updatedAt` được cập nhật mỗi lần thay đổi
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    // Getters và Setters thủ công
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return this.token;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return this.role;
    }
    public enum Role {
        USER,
        ADMIN,
        SELLER,
        CUSTOMER,
        SHIPPER,
    }
    
}
