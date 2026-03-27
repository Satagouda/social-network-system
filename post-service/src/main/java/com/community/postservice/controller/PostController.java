package com.community.postservice.controller;

import com.community.postservice.dto.PostRequest;
import com.community.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> like(@PathVariable String postId) {
        service.like(postId);
        return ResponseEntity.ok("Liked");
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> comment(@PathVariable String postId,
                                     @RequestBody Comment comment) {
        service.comment(postId, comment);
        return ResponseEntity.ok("Comment added");
    }

    @PostMapping("/{postId}/repost/{userId}")
    public ResponseEntity<?> repost(@PathVariable String postId,
                                    @PathVariable String userId) {
        return ResponseEntity.ok(service.repost(postId, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPosts(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserPosts(userId));
    }
}