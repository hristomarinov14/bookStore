package com.example.bookStore.service;

import com.example.bookStore.exception.ResourceNotFoundException;
import com.example.bookStore.model.Order;
import com.example.bookStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrdersBasedOnCustomer(Long customerId) {
        return this.orderRepository.findByCustomer_Id(customerId);
    }

    public Optional<Order> findOrderById(Long id) {
        return this.orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        this.orderRepository.deleteById(id);
    }
}
