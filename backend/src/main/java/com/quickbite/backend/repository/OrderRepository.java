package com.quickbite.backend.repository;

import com.quickbite.backend.entity.Order; // <-- SIRF YEH HONA CHAHIYE
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}