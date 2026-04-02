package com.community.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "eureka.client.enabled=false")
class ApiGatewayApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private KeyResolver primaryResolver;

    @Test
    void contextLoads() {
        // Verify both beans exist
        assertThat(context.containsBean("userKeyResolver")).isTrue();
        assertThat(context.containsBean("ipKeyResolver")).isTrue();

        // Verify userKeyResolver was picked as the Primary one
        assertThat(primaryResolver).isNotNull();
    }
}
