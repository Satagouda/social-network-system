package com.community.feedservice.repository;

import com.community.feedservice.entity.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeedRepository extends MongoRepository<Feed, String> {
    Optional<Feed> findByUserId(String userId);
}
