package com.quickbite.backend.entity;

import com.quickbite.backend.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders") // "order" SQL mein reserved keyword hota hai, isliye "orders" likha
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kis user ne order kiya
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Ek order mein multiple items ho sakte hain (Jaise 1 Pizza, 2 Coke)
    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Helper method: Order mein items easily add karne ke liye
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}