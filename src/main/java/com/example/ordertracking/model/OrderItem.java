package com.example.ordertracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents a single line item within an order.
 * <p>
 * This is an embedded (nested) document, not a separate MongoDB collection -
 * it is stored directly inside the parent {@link Order} document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    /** Identifier of the product being ordered (e.g. SKU). */
    private String productId;

    /** Human-readable product name, stored for convenience/history. */
    private String productName;

    /** Number of units of this product ordered. */
    private Integer quantity;

    /** Price per single unit at the time the order was placed. */
    private BigDecimal unitPrice;

}
