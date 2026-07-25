package com.example.ordertracking.service;

import com.example.ordertracking.dto.CreateOrderRequest;
import com.example.ordertracking.dto.OrderResponse;
import com.example.ordertracking.dto.UpdateOrderRequest;
import com.example.ordertracking.model.OrderStatus;

import java.util.List;

/**
 * Business logic contract for managing orders.
 * <p>
 * The controller layer depends only on this interface, not on the
 * implementation, which keeps the two layers loosely coupled and makes
 * the service easy to mock in tests.
 */
public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(String id);

    OrderResponse getOrderByOrderNumber(String orderNumber);

    OrderResponse getOrderByTrackingNumber(String trackingNumber);

    OrderResponse updateOrder(String id, UpdateOrderRequest request);

    OrderResponse updateOrderStatus(String id, OrderStatus newStatus);

    void deleteOrder(String id);

}
