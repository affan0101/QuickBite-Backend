package com.quickbite.backend.controller;

import com.quickbite.backend.dto.response.OrderResponse;
import com.quickbite.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        List<OrderResponse> responses=orderService.getAllOrders();

                return ResponseEntity.ok(responses);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status){
        OrderResponse response = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(response);
    }
}
