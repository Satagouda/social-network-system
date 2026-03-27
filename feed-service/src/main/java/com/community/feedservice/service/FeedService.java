package com.community.feedservice.service;

import com.community.commonlib.exception.GlobalException;
import com.community.feedservice.entity.Feed;
import com.community.feedservice.entity.FeedItem;
import com.community.feedservice.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository repository;

    // Called when a user creates a post
    public void updateFollowersFeed(String userId, String postId, String content, List<String> followers) {

        for (String followerId : followers) {

            Feed feed = repository.findByUserId(followerId)
                    .orElseGet(() -> {
                        Feed f = new Feed();
                        f.setUserId(followerId);
                        return f;
                    });

            FeedItem item = new FeedItem();
            item.setPostId(postId);
            item.setUserId(userId);
            item.setContent(content);

            feed.getItems().add(0, item); // latest on top

            repository.save(feed);
        }
    }

    public List<FeedItem> getFeed(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException("Feed not found"))
                .getItems();
    }
}