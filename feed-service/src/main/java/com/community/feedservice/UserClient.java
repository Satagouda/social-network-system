package com.community.feedservice;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class UserClient {

    private final WebClient webClient = WebClient.create("http://USER-SERVICE");

    public List<String> getFollowers(String userId) {
        return webClient.get()
                .uri("/users/{userId}/followers", userId)
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }
}