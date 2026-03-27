package com.community.feedservice.controller;

import com.community.feedservice.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService service;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFeed(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(service.getFeed(userId));
    }
}