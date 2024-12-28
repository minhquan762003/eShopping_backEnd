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
import com.example.E_Shopping.model.OrderItems;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.service.OrderItemService;

@RestController
@RequestMapping("/api/orderItems")

public class OrderItemController {
    @Autowired
    OrderItemService orderItemService;
    @GetMapping
    public List<OrderItems> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }
    @GetMapping("/{orderId}")
    public List<OrderItems> getAllOrderItemsByOrderId(@PathVariable Long orderId){
        return orderItemService.getAllOrderItemsByOrderId(orderId);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> saveOrderItem(@RequestBody OrderItems OrderItem) {
        ResponseObject responseObject = orderItemService.saveOrderItem(OrderItem);
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<ResponseObject> deleteOrderItemById(@PathVariable Long orderItemId) {
        boolean exist = orderItemService.existById(orderItemId);
        if (exist) {
            ResponseObject responseObject = orderItemService.deleteOrderItemById(orderItemId);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Khong co don hang = " + orderItemId + " de xoa", ""));
    }

    @PutMapping
    ResponseEntity<ResponseObject> updateOrderItemById(@RequestBody OrderItems newOrderItem, @PathVariable Long id) {
        ResponseObject responseObject = orderItemService.updateStudentById(newOrderItem, id);
        if ("Failed".equals(responseObject.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }

    // @GetMapping("/{id}")
    // ResponseEntity<ResponseObject> getOrderItemById(@PathVariable Long id) {
    //     ResponseObject responseObject = orderItemService.getOrderItemById(id);
    //     if ("Failed".equals(responseObject.getStatus())) {
    //         return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
    //     }
    //     return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    // }
}
