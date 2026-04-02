package com.community.feedservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Bean
    public DefaultErrorHandler errorHandler() {
        // retry 3 times, 2 sec gap
        return new DefaultErrorHandler(new FixedBackOff(2000L, 3));
    }
}
