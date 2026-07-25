package com.example.ordertracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * MongoDB document representing a customer order.
 * <p>
 * Stored in the "orders" collection. {@code orderNumber} and
 * {@code trackingNumber} are indexed (and unique where applicable) to make
 * lookups by those fields fast, since the API exposes dedicated endpoints
 * for both.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {

    /** MongoDB-generated primary key (ObjectId as a String). */
    @Id
    private String id;

    /** Unique, human-friendly order identifier, e.g. "ORD-20260725-ABC123". */
    @Indexed(unique = true)
    private String orderNumber;

    private String customerName;

    private String customerEmail;

    private String shippingAddress;

    /** List of products purchased as part of this order. */
    private List<OrderItem> items;

    /** Total monetary value of the order. */
    private BigDecimal totalAmount;

    /** Current status of the order in its lifecycle. */
    private OrderStatus orderStatus;

    /** Carrier tracking number, populated once the order has shipped. */
    @Indexed(sparse = true)
    private String trackingNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
