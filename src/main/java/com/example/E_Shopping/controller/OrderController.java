package com.example.E_Shopping.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.E_Shopping.model.Orders;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.model.Users;
import com.example.E_Shopping.service.OrderService;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/user/{userId}")
    public Optional<Users> getUserByUserId(@PathVariable Long userId) {
        return orderService.getUserByUserId(userId);
    }

    @GetMapping("/{userId}")
    public List<Orders> getOrdersByUserId(@PathVariable Long userId) {
        {
            return orderService.getOrderByUserId(userId);
        }
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    // @GetMapping("/{id}")
    // public List<Orders> getAllOrdersByUserId(Long userId) {
    // return orderService.getOrderByUserId(userId);
    // }

    @PostMapping
    public ResponseEntity<ResponseObject> saveOrder(@RequestBody Orders order) {
        ResponseObject responseObject = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> deleteOrderById(@PathVariable Long id) {
        boolean exist = orderService.existById(id);
        if (exist) {
            ResponseObject responseObject = orderService.deleteOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Khong co don hang = " + id + " de xoa", ""));
    }

    @PutMapping
    ResponseEntity<ResponseObject> updateOrderById(@RequestBody Orders newOrder, @PathVariable Long id) {
        ResponseObject responseObject = orderService.updateStudentById(newOrder, id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    // @GetMapping("/{id}")
    // ResponseEntity<ResponseObject> getOrderById(@PathVariable Long id) {
    // ResponseObject responseObject = orderService.getOrderById(id);
    // if ("Failed".equals(responseObject.getStatus())) {
    // return
    // ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
    // }
    // return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    // }
}
