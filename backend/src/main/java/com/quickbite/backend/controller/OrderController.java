package com.quickbite.backend.controller;

import com.quickbite.backend.dto.request.OrderRequest;
import com.quickbite.backend.dto.response.OrderResponse;
import com.quickbite.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest , Principal principal){
        OrderResponse response=orderService.placeOrder(orderRequest, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(Principal principal){
        List<OrderResponse> responses=orderService.getMyOrders(principal.getName());
        return ResponseEntity.ok(responses);
    }
}
