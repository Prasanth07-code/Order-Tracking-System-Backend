package com.example.ordertracking.dto;

import com.example.ordertracking.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response representation of an order, returned by all read/write endpoints.
 * <p>
 * Kept separate from the {@code Order} document so that the internal
 * persistence model can evolve independently of the public API contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String id;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String shippingAddress;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
