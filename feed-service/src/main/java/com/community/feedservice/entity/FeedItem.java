package com.community.feedservice.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedItem {

    private String postId;
    private String userId;
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}