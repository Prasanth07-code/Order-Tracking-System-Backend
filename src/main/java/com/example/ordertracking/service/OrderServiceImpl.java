package com.example.ordertracking.service;

import com.example.ordertracking.dto.CreateOrderRequest;
import com.example.ordertracking.dto.OrderItemRequest;
import com.example.ordertracking.dto.OrderItemResponse;
import com.example.ordertracking.dto.OrderResponse;
import com.example.ordertracking.dto.UpdateOrderRequest;
import com.example.ordertracking.exception.ResourceNotFoundException;
import com.example.ordertracking.model.Order;
import com.example.ordertracking.model.OrderItem;
import com.example.ordertracking.model.OrderStatus;
import com.example.ordertracking.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link OrderService}.
 * <p>
 * Contains all business rules for orders: generating identifiers, stamping
 * timestamps, enforcing status transitions where relevant, and mapping
 * between the persistence model ({@link Order}) and the API DTOs.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Constructor injection (preferred over field injection): Spring will
     * automatically supply the OrderRepository bean here.
     */
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ---------------------------------------------------------------
    // Create
    // ---------------------------------------------------------------

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        LocalDateTime now = LocalDateTime.now();

        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .shippingAddress(request.getShippingAddress())
                .items(mapItemRequestsToItems(request.getItems()))
                .totalAmount(request.getTotalAmount())
                .orderStatus(OrderStatus.CREATED)
                .trackingNumber(null) // assigned later, once the order ships
                .createdAt(now)
                .updatedAt(now)
                .build();

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    // ---------------------------------------------------------------
    // Read
    // ---------------------------------------------------------------

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(String id) {
        Order order = findOrderOrThrow(id);
        return mapToResponse(order);
    }

    @Override
    public OrderResponse getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with orderNumber: " + orderNumber));
        return mapToResponse(order);
    }

    @Override
    public OrderResponse getOrderByTrackingNumber(String trackingNumber) {
        Order order = orderRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with trackingNumber: " + trackingNumber));
        return mapToResponse(order);
    }

    // ---------------------------------------------------------------
    // Update
    // ---------------------------------------------------------------

    @Override
    public OrderResponse updateOrder(String id, UpdateOrderRequest request) {
        Order order = findOrderOrThrow(id);

        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setItems(mapItemRequestsToItems(request.getItems()));
        order.setTotalAmount(request.getTotalAmount());

        if (request.getTrackingNumber() != null && !request.getTrackingNumber().isBlank()) {
            order.setTrackingNumber(request.getTrackingNumber());
        }

        order.setUpdatedAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    @Override
    public OrderResponse updateOrderStatus(String id, OrderStatus newStatus) {
        Order order = findOrderOrThrow(id);

        order.setOrderStatus(newStatus);

        // Auto-generate a tracking number the first time an order is marked SHIPPED,
        // if one hasn't already been assigned.
        if (newStatus == OrderStatus.SHIPPED && (order.getTrackingNumber() == null || order.getTrackingNumber().isBlank())) {
            order.setTrackingNumber(generateTrackingNumber());
        }

        order.setUpdatedAt(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    // ---------------------------------------------------------------
    // Delete
    // ---------------------------------------------------------------

    @Override
    public void deleteOrder(String id) {
        Order order = findOrderOrThrow(id);
        orderRepository.delete(order);
    }

    // ---------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------

    private Order findOrderOrThrow(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    /**
     * Generates a unique, human-readable order number, e.g.
     * "ORD-20260725143210-4F2A9B".
     */
    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomSuffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + timestamp + "-" + randomSuffix;
    }

    /**
     * Generates a mock carrier tracking number, e.g. "TRK-7F3E9A2C1B".
     * In a real system this would typically come from a shipping provider's API.
     */
    private String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    private List<OrderItem> mapItemRequestsToItems(List<OrderItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(itemRequest -> OrderItem.builder()
                        .productId(itemRequest.getProductId())
                        .productName(itemRequest.getProductName())
                        .quantity(itemRequest.getQuantity())
                        .unitPrice(itemRequest.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

    private List<OrderItemResponse> mapItemsToItemResponses(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .shippingAddress(order.getShippingAddress())
                .items(mapItemsToItemResponses(order.getItems()))
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .trackingNumber(order.getTrackingNumber())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}
