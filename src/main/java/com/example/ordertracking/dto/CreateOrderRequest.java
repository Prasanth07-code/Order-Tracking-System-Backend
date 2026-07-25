package com.example.ordertracking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request payload for creating a new order via POST /api/orders.
 */
@Data
public class CreateOrderRequest {

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "customerEmail is required")
    @Email(message = "customerEmail must be a valid email address")
    private String customerEmail;

    @NotBlank(message = "shippingAddress is required")
    private String shippingAddress;

    @NotNull(message = "items is required")
    @NotEmpty(message = "items must contain at least one product")
    @Valid
    private List<OrderItemRequest> items;

    @NotNull(message = "totalAmount is required")
    @Positive(message = "totalAmount must be greater than 0")
    private BigDecimal totalAmount;

}
