package com.quickbite.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quickbite.backend.entity.Product;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
    private Double price; // Us waqt dish ka kya price tha

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore // Infinite recursion rokne ke liye
    private Order order;
}