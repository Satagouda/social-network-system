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

    //  CALLED FROM KAFKA CONSUMER
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

            //  LATEST FIRST
            feed.getItems().add(0, item);

            //  LIMIT FEED SIZE (ADD HERE ONLY)
            if (feed.getItems().size() > 100) {
                feed.getItems().remove(feed.getItems().size() - 1);
            }

            repository.save(feed);
        }
    }

    //  PAGINATION
    public List<FeedItem> getFeed(String userId, int page, int size) {

        Feed feed = repository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException("Feed not found for userId: " + userId));

        List<FeedItem> items = feed.getItems();

        int start = page * size;
        int end = Math.min(start + size, items.size());

        if (start >= items.size()) {
            return List.of();
        }

        return items.subList(start, end);
    }
}