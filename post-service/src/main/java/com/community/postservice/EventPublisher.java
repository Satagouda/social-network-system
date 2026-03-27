package com.community.postservice;

import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    public void publishPostCreated(String userId, String postId, String content) {
        System.out.println("EVENT: Post Created → " + postId);
    }
}
