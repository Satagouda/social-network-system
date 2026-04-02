package com.community.postservice.service;

import com.community.commonlib.exception.GlobalException;
import com.community.postservice.EventPublisher;
import com.community.postservice.dto.PostRequest;
import com.community.postservice.entity.Comment;
import com.community.postservice.entity.Post;
import com.community.postservice.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final EventPublisher eventPublisher;
//    private final RateLimiter rateLimiter;

    public Post create(PostRequest request) {
//        rateLimiter.validate(request.getUserId());
        Post post = new Post();
        post.setUserId(request.getUserId());
        post.setContent(request.getContent());
        post.setType(request.getType());
        post.setMediaUrl(request.getMediaUrl());

        Post saved = repository.save(post);

        eventPublisher.publishPostCreated(
                saved.getUserId(),
                saved.getId(),
                saved.getContent()
        );

        return saved;
    }

    // LIKE (eventual consistency concept)
    public void like(String postId) {
        Post post = repository.findById(postId)
                .orElseThrow(() -> new GlobalException("Post not found"));

        post.setLikeCount(post.getLikeCount() + 1);
        repository.save(post);
    }

    public void comment(String postId, Comment comment) {
        Post post = repository.findById(postId)
                .orElseThrow(() -> new GlobalException("Post not found"));

        post.getComments().add(comment);
        repository.save(post);
    }

    public Post repost(String postId, String userId) {
        Post original = repository.findById(postId)
                .orElseThrow(() -> new GlobalException("Post not found"));

        Post repost = new Post();
        repost.setUserId(userId);
        repost.setContent(original.getContent());
        repost.setRepostOf(postId);

        return repository.save(repost);
    }

    public List<Post> getUserPosts(String userId) {
        return repository.findByUserId(userId);
    }
}
