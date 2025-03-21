package com.example.E_Shopping.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "usersearchhistory")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idUserSearchHistory;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public void setUser(Users user) {
        this.user = user;
    }

    @Column(name = "search_term")
    private String searchTerm;

    public Long getIdUserSearchHistory() {
        return idUserSearchHistory;
    }

    public void setIdUserSearchHistory(Long idUserSearchHistory) {
        this.idUserSearchHistory = idUserSearchHistory;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Date getSearchTime() {
        return searchTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "search_time")
    private Date searchTime = new Date();

    public Users getUser() {
        return user;
    }

}
