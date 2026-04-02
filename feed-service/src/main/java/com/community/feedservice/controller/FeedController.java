package com.community.feedservice.controller;

import com.community.feedservice.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService service;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFeed(
            @PathVariable("userId") String userId,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size) {

        return ResponseEntity.ok(service.getFeed(userId, page, size));
    }
}