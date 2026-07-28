package com.quickbite.backend.service;

// --- DTO Imports ---
import com.quickbite.backend.repository.OrderRepository;
import com.quickbite.backend.repository.ProductRepository;
import com.quickbite.backend.repository.UserRepository;
import com.quickbite.backend.dto.request.OrderItemRequest;
import com.quickbite.backend.dto.request.OrderRequest;
import com.quickbite.backend.dto.response.OrderItemResponse;
import com.quickbite.backend.dto.response.OrderResponse;

// --- Entity Imports ---
import com.quickbite.backend.entity.Order;         // <-- YEH WALA CORRECT HAI
import com.quickbite.backend.entity.OrderItem;
import com.quickbite.backend.entity.Product;
import com.quickbite.backend.entity.User;
import com.quickbite.backend.entity.enums.OrderStatus;

// --- Repository Imports ---


// --- Spring Boot & Java Imports ---
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // 1. CUSTOMER: Place a New Order
    public OrderResponse placeOrder(OrderRequest request, String userEmail) {
        // Step A: Kis user ne order diya hai usko dhoondo
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Step B: Ek naya khali Order (Bill) banao
        Order order = Order.builder()
                .user(user)
                .orderStatus(OrderStatus.PENDING) // Default status hamesha PENDING hoga
                .createdAt(new Date())
                .totalAmount(0.0) // Shuruat mein total bill 0 hai
                .build();

        double calculatedTotal = 0.0;

        // Step C: Customer ne jo jo items bheje hain, unhe process karo
        for (OrderItemRequest itemRequest : request.getItems()) {
            // DATABASE se original dish nikalo (Frontend se price par bharosa mat karo)
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

            // Bill ki ek line (OrderItem) banao
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice()) // DB wala asli price set karo
                    .build();

            // Is item ko main Bill (Order) mein add kar do (Helper method call)
            order.addOrderItem(orderItem);

            // Total price calculate karo: (Dish ka daam * Kitni plate li)
            calculatedTotal += (product.getPrice() * itemRequest.getQuantity());
        }

        // Final total amount ko order mein set karo
        order.setTotalAmount(calculatedTotal);

        // Step D: Order save karo (Cascade.ALL ki wajah se OrderItems khud save ho jayenge!)
        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponse(savedOrder);
    }

    // 2. CUSTOMER: Apne purane orders dekhna
    public List<OrderResponse> getMyOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Custom repository method call kiya
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());

        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    // 3. ADMIN: Saare customers ke orders dekhna
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    // 4. ADMIN: Order ka status change karna (e.g., PENDING -> PREPARING)
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        // String ko Enum mein convert karke set karo
        try {
            order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Order Status: " + status);
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToOrderResponse(updatedOrder);
    }

    // --- HELPER METHODS ---

    // Order Entity ko OrderResponse DTO mein badalna
    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponse)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerName(order.getUser().getName())
                .totalAmount(order.getTotalAmount())
                .status(order.getOrderStatus().name())
                .orderDate(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

    // OrderItem Entity ko OrderItemResponse DTO mein badalna
    private OrderItemResponse mapToOrderItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}