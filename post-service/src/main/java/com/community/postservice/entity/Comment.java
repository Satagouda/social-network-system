package com.community.postservice.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private String userId;
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();
}
