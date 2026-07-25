package com.example.ordertracking.model;

/**
 * Represents the lifecycle states of an order, in the order they are
 * typically expected to occur.
 */
public enum OrderStatus {
    CREATED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
