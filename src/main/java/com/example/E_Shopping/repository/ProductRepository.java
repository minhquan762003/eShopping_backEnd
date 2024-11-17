package com.example.E_Shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.E_Shopping.model.Products;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:name%")
    List<Products> findByNameContaining(@Param("name") String name);
}
