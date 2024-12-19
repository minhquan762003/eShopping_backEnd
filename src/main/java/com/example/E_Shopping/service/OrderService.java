package com.example.E_Shopping.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.E_Shopping.model.Orders;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.Users;
import com.example.E_Shopping.repository.OrderRepository;
import com.example.E_Shopping.repository.UserRepository;

@Service
public class OrderService implements WebMvcConfigurer {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    public List<Orders> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserUserId(userId);
    }
    public Optional<Users> getUserByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public ResponseObject saveOrder(Orders order) {
        Orders savedOrder = orderRepository.save(order);
        return new ResponseObject("ok", "Dat san pham thanh cong", savedOrder);
    }

    public ResponseObject getOrderById(Long id) {
        Optional<Orders> foundOrder = orderRepository.findById(id);
        if (foundOrder.isPresent()) {
            return new ResponseObject("ok", "Tim san pham thanh cong", foundOrder);
        }
        return new ResponseObject("Failed", "Khong tim thay san pham", "");
    }

    public List<Orders> getOrderByUserId(Long userId) {
        return orderRepository.findByUserUserId(userId);

    }
    public ResponseObject deleteOrderById(Long id) {
        boolean exist = orderRepository.existsById(id);
        if (exist) {
            orderRepository.deleteById(id);
            return new ResponseObject("ok", "Xoa san pham thanh cong", getOrderById(id));
        }

        return new ResponseObject("Failed", "Khong tim duoc san pham id = " + id + " de xoa ", "");
    }

    public ResponseObject updateStudentById(Orders newOrder, Long id) {
        if (existById(id)) {
            Orders updatedOrder = orderRepository.findById(id).map(
                    order -> {
                        order.setTotalAmount(newOrder.getTotalAmount());
                        order.setStatus(newOrder.getStatus());;
                        return orderRepository.save(order);
                    }).orElseGet(() -> {
                        return orderRepository.save(newOrder);
                    });

            return new ResponseObject("ok", "Sua thong tin san pham thanh cong", updatedOrder);
        } else {
            return new ResponseObject("Failed", "Sua khong thanh cong", "");
        }

    }

    public boolean existById(Long id) {
        return this.orderRepository.existsById(id);
    }
}
