package com.example.E_Shopping.repository;

import org.springframework.stereotype.Repository;

import com.example.E_Shopping.model.SearchHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserUserId(Long userId);
    Optional<SearchHistory> findBySearchTerm(String searchTerm);
}
