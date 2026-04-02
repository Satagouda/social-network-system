package com.community.postservice;

import com.community.commonlib.dto.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, PostCreatedEvent> kafkaTemplate;

    private static final String TOPIC = "post-created";

    public void publishPostCreated(String userId, String postId, String content) {
        PostCreatedEvent event = new PostCreatedEvent(userId, postId, content);
        kafkaTemplate.send(TOPIC, event);
    }
}
