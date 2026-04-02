package com.community.userservice.controller;

import com.community.userservice.dto.UserRequest;
import com.community.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/{userId}/follow/{targetId}")
    public ResponseEntity<?> follow(@PathVariable("userId") String userId,
                                    @PathVariable("targetId") String targetId) {
        service.follow(userId, targetId);
        return ResponseEntity.ok("Followed");
    }

    @PostMapping("/{userId}/unfollow/{targetId}")
    public ResponseEntity<?> unfollow(@PathVariable("userId") String userId,
                                      @PathVariable("targetId") String targetId) {
        service.unfollow(userId, targetId);
        return ResponseEntity.ok("Unfollowed");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<?> followers(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(service.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<?> following(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(service.getFollowing(userId));
    }
}
