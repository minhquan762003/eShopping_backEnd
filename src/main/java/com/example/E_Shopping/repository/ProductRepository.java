package com.example.E_Shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.E_Shopping.model.Products;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    
    @Query("SELECT p FROM Products p WHERE p.name LIKE %:name%")
    List<Products> findByNameContaining(@Param("name") String name);
    List<Products> findByCategory(String category);
}
