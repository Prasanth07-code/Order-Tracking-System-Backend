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
 * Request payload for updating an existing order via PUT /api/orders/{id}.
 * <p>
 * All fields are required (this is a full replace of the editable order
 * data). Order status is intentionally excluded here - use
 * PATCH /api/orders/{id}/status to change the status.
 */
@Data
public class UpdateOrderRequest {

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

    /** Optional: allows updating the tracking number alongside other details. */
    private String trackingNumber;

}
