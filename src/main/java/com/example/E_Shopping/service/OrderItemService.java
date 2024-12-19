package com.example.E_Shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.*;

import com.example.E_Shopping.model.OrderItems;
import com.example.E_Shopping.model.ResponseObject;
import com.example.E_Shopping.repository.OrderItemRepository;

@Service
public class OrderItemService implements WebMvcConfigurer {


    @Autowired
    OrderItemRepository orderItemRepository;

    public List<OrderItems> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
    public List<OrderItems> getAllOrderItemsByOrderId(Long orderId){
        return orderItemRepository.findByOrderOrderId(orderId);
    }

    public ResponseObject saveOrderItem(OrderItems orderItem) {
        OrderItems savedOrderItem = orderItemRepository.save(orderItem);
        return new ResponseObject("ok", "Dat san pham thanh cong", savedOrderItem);
    }

    public ResponseObject getOrderItemById(Long id) {
        Optional<OrderItems> foundOrderItem = orderItemRepository.findById(id);
        if (foundOrderItem.isPresent()) {
            return new ResponseObject("ok", "Tim san pham thanh cong", foundOrderItem);
        }
        return new ResponseObject("Failed", "Khong tim thay san pham", "");
    }

    public ResponseObject deleteOrderItemById(Long id) {
        boolean exist = orderItemRepository.existsById(id);
        if (exist) {
            orderItemRepository.deleteById(id);
            return new ResponseObject("ok", "Xoa san pham thanh cong", getOrderItemById(id));
        }

        return new ResponseObject("Failed", "Khong tim duoc san pham id = " + id + " de xoa ", "");
    }

    public ResponseObject updateStudentById(OrderItems newOrderItem, Long id) {
        if (existById(id)) {
            OrderItems updatedOrderItem = orderItemRepository.findById(id).map(
                    orderItem -> {
                        orderItem.setQuantity(newOrderItem.getQuantity());
                        orderItem.setPrice(newOrderItem.getPrice());
                        return orderItemRepository.save(orderItem);
                    }).orElseGet(() -> {
                        return orderItemRepository.save(newOrderItem);
                    });

            return new ResponseObject("ok", "Sua thong tin san pham thanh cong", updatedOrderItem);
        } else {
            return new ResponseObject("Failed", "Sua khong thanh cong", "");
        }

    }

    public boolean existById(Long id) {
        return this.orderItemRepository.existsById(id);
    }

}
