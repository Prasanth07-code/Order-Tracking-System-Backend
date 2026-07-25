package com.example.ordertracking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic smoke test that verifies the Spring application context loads.
 * <p>
 * Note: this requires a reachable MongoDB instance (local or Atlas) at the
 * URI supplied via the MONGODB_URI environment variable, since Spring Data
 * MongoDB will attempt to initialize the connection on startup.
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.data.mongodb.uri=mongodb://localhost:27017/order_tracking_test_db"
})
class OrderTrackingApplicationTests {

    @Test
    void contextLoads() {
        // If the application context fails to start, this test will fail.
    }

}
