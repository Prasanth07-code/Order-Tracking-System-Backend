package com.example.ordertracking.repository;

import com.example.ordertracking.model.Order;
import com.example.ordertracking.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for {@link Order} documents.
 * <p>
 * Extending {@link MongoRepository} automatically provides CRUD operations
 * (save, findById, findAll, deleteById, etc.). The extra finder methods
 * below are implemented automatically by Spring Data based on their method
 * names (query derivation) - no implementation code is needed.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    /** Finds an order by its unique, human-friendly order number. */
    Optional<Order> findByOrderNumber(String orderNumber);

    /** Finds an order by its carrier tracking number. */
    Optional<Order> findByTrackingNumber(String trackingNumber);

    /** Finds all orders currently in a given status. */
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    /** Checks whether an order number is already in use (used for uniqueness). */
    boolean existsByOrderNumber(String orderNumber);

}
