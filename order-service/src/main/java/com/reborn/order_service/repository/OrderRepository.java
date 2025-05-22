package com.reborn.order_service.repository;

import com.reborn.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccountId(Long accountId);
} 