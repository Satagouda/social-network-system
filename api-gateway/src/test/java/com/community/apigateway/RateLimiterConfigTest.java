package com.community.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "eureka.client.enabled=false")
class RateLimiterConfigTest {

    @Autowired
    @Qualifier("userKeyResolver")
    private KeyResolver userKeyResolver;

    @Autowired
    @Qualifier("ipKeyResolver")
    private KeyResolver ipKeyResolver;

    @Test
    void testUserKeyResolver_WithHeader() {
        // Simulate a request with the X-User-Id header
        MockServerHttpRequest request = MockServerHttpRequest.get("/any")
                .header("X-User-Id", "user-123")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        // Verify the resolver extracts "user-123"
        StepVerifier.create(userKeyResolver.resolve(exchange))
                .expectNext("user-123")
                .verifyComplete();
    }

    @Test
    void testUserKeyResolver_Anonymous() {
        // Simulate a request WITHOUT the header
        MockServerHttpRequest request = MockServerHttpRequest.get("/any").build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        // Verify it falls back to "anonymous"
        StepVerifier.create(userKeyResolver.resolve(exchange))
                .expectNext("anonymous")
                .verifyComplete();
    }

    @Test
    void testIpKeyResolver() {
        // Simulate a request from a specific IP
        MockServerHttpRequest request = MockServerHttpRequest.get("/any")
                .remoteAddress(new java.net.InetSocketAddress("192.168.1.1", 8080))
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        // Verify it extracts the IP address correctly
        StepVerifier.create(ipKeyResolver.resolve(exchange))
                .expectNext("192.168.1.1")
                .verifyComplete();
    }
}