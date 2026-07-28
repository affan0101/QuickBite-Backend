package com.quickbite.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private String customerName;
    private Double totalAmount;
    private String status;
    private Date orderDate;
    private List<OrderItemResponse> items;


}
