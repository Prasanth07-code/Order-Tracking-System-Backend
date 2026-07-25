package com.example.ordertracking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.ordertracking.model.OrderStatus;

/**
 * Request payload for PATCH /api/orders/{id}/status - updates only the
 * order status field.
 */
@Data
public class UpdateOrderStatusRequest {

    @NotNull(message = "orderStatus is required")
    private OrderStatus orderStatus;

}
