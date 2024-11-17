package com.example.E_Shopping.model;

import java.util.*;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    // Đoạn mã này đảm bảo `updatedAt` được cập nhật mỗi lần thay đổi
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
