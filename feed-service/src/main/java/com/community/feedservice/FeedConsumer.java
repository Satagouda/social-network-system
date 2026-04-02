package com.community.feedservice;

import com.community.commonlib.dto.PostCreatedEvent;
import com.community.feedservice.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedConsumer {

    private final FeedService feedService;
    private final UserClient userClient;

    //  MAIN CONSUMER
    @KafkaListener(topics = "post-created", groupId = "feed-group")
    public void consume(PostCreatedEvent event) {

        List<String> followers = userClient.getFollowers(event.getUserId());

        feedService.updateFollowersFeed(
                event.getUserId(),
                event.getPostId(),
                event.getContent(),
                followers
        );
    }

    //  DLQ CONSUMER (PUT HERE ONLY)
    @KafkaListener(topics = "post-created.DLT", groupId = "feed-group")
    public void consumeDLQ(Object event) {
        System.out.println("DLQ EVENT: " + event);
    }
}