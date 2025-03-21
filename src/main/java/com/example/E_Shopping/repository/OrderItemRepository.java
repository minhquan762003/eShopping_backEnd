package com.example.E_Shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.E_Shopping.model.OrderItems;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long>{
    List<OrderItems> findByOrderOrderId(Long orderId);
}
