package com.example.ordertracking.controller;

import com.example.ordertracking.dto.ApiResponse;
import com.example.ordertracking.dto.CreateOrderRequest;
import com.example.ordertracking.dto.OrderResponse;
import com.example.ordertracking.dto.UpdateOrderRequest;
import com.example.ordertracking.dto.UpdateOrderStatusRequest;
import com.example.ordertracking.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing the Order Tracking System API under /api/orders.
 * <p>
 * This layer is intentionally thin: it validates input (via @Valid),
 * delegates all business logic to {@link OrderService}, and wraps every
 * result in the standard {@link ApiResponse} envelope.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /** Constructor injection of the service dependency. */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST /api/orders
     * Creates a new order.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse created = orderService.createOrder(request);
        ApiResponse<OrderResponse> response = ApiResponse.success("Order created successfully", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/orders
     * Returns all orders.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        ApiResponse<List<OrderResponse>> response = ApiResponse.success("Orders retrieved successfully", orders);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/{id}
     * Returns a single order by its MongoDB id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable String id) {
        OrderResponse order = orderService.getOrderById(id);
        ApiResponse<OrderResponse> response = ApiResponse.success("Order retrieved successfully", order);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/order-number/{orderNumber}
     * Returns a single order by its business order number.
     */
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByOrderNumber(
            @PathVariable String orderNumber) {

        OrderResponse order = orderService.getOrderByOrderNumber(orderNumber);
        ApiResponse<OrderResponse> response = ApiResponse.success("Order retrieved successfully", order);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/tracking/{trackingNumber}
     * Returns a single order by its carrier tracking number.
     */
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderByTrackingNumber(
            @PathVariable String trackingNumber) {

        OrderResponse order = orderService.getOrderByTrackingNumber(trackingNumber);
        ApiResponse<OrderResponse> response = ApiResponse.success("Order retrieved successfully", order);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/orders/{id}
     * Fully updates an existing order's editable fields.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable String id,
            @Valid @RequestBody UpdateOrderRequest request) {

        OrderResponse updated = orderService.updateOrder(id, request);
        ApiResponse<OrderResponse> response = ApiResponse.success("Order updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/orders/{id}/status
     * Updates only the order status (e.g. moving it to SHIPPED).
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        OrderResponse updated = orderService.updateOrderStatus(id, request.getOrderStatus());
        ApiResponse<OrderResponse> response = ApiResponse.success("Order status updated successfully", updated);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/orders/{id}
     * Deletes an order permanently.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        ApiResponse<Object> response = ApiResponse.success("Order deleted successfully");
        return ResponseEntity.ok(response);
    }

}
