package com.example.ordertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Order Tracking System application.
 * <p>
 * This is a Spring Boot application that exposes a REST API for creating,
 * retrieving, updating and deleting customer orders, backed by MongoDB Atlas.
 */
@SpringBootApplication
public class OrderTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderTrackingApplication.class, args);
    }

}
