package com.reborn.order_service.service;

import com.reborn.order_service.model.Order;
import com.reborn.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setAccountId(100L);
        testOrder.setStatus("PENDING");
        testOrder.setTotalAmount(99.99);
        testOrder.setCreatedAt(LocalDateTime.now());
        testOrder.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order createdOrder = orderService.createOrder(testOrder);

        assertNotNull(createdOrder);
        assertEquals(testOrder.getId(), createdOrder.getId());
        assertEquals(testOrder.getAccountId(), createdOrder.getAccountId());
        assertEquals(testOrder.getStatus(), createdOrder.getStatus());
        assertEquals(testOrder.getTotalAmount(), createdOrder.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrdersByAccount_Success() {
        List<Order> expectedOrders = Arrays.asList(testOrder);
        when(orderRepository.findByAccountId(100L)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getOrdersByAccount(100L);

        assertNotNull(actualOrders);
        assertEquals(1, actualOrders.size());
        assertEquals(testOrder.getId(), actualOrders.get(0).getId());
        verify(orderRepository, times(1)).findByAccountId(100L);
    }

    @Test
    void getOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(testOrder.getId(), foundOrder.getId());
        assertEquals(testOrder.getAccountId(), foundOrder.getAccountId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderById_NotFound() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrderById(999L));
        verify(orderRepository, times(1)).findById(999L);
    }
} 