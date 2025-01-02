package com.example.bookStore.service;

import com.example.bookStore.dto.OrderItemDto;
import com.example.bookStore.exception.ResourceNotFoundException;
import com.example.bookStore.model.OrderItem;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.OrderItemRepository;
import com.example.bookStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
    }

    public List<OrderItem> findAllByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrder_Id(orderId);
    }

    public List<OrderItem> findAllByProductId(Long productId) {
        return orderItemRepository.findAllByProduct_Id(productId);
    }

    public List<OrderItem> findAllWithQuantityGreaterThan(int quantity) {
        return orderItemRepository.findAllWithQuantityGreaterThan(quantity);
    }

    public OrderItem updateOrderItemQuantity(Long orderItemId, int quantity) {
        var orderItem = findById(orderItemId);
        return orderItemRepository.save(orderItem);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    public OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();

        var orderId = orderRepository.findById(orderItem.getOrder().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        var bookId = bookRepository.findById(orderItem.getBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        dto.setOrderId(orderId.getId());
        dto.setBookId(bookId.getId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        return dto;
    }

    public OrderItem convertToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());

        var order = orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        var book = bookRepository.findById(orderItemDto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        orderItem.setOrder(order);
        orderItem.setBook(book);

        return orderItem;
    }
}
