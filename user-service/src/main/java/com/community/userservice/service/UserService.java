package com.community.userservice.service;

import com.community.commonlib.exception.GlobalException;
import com.community.userservice.dto.UserRequest;
import com.community.userservice.entity.User;
import com.community.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User register(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        return repository.save(user);
    }

    public void follow(String userId, String targetId) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new GlobalException("User not found"));

        User target = repository.findById(targetId)
                .orElseThrow(() -> new GlobalException("Target not found"));

        user.getFollowing().add(targetId);
        target.getFollowers().add(userId);

        repository.save(user);
        repository.save(target);
    }

    public void unfollow(String userId, String targetId) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new GlobalException("User not found"));

        User target = repository.findById(targetId)
                .orElseThrow(() -> new GlobalException("Target not found"));

        user.getFollowing().remove(targetId);
        target.getFollowers().remove(userId);

        repository.save(user);
        repository.save(target);
    }

    public Set<String> getFollowers(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new GlobalException("User not found"))
                .getFollowers();
    }

    public Set<String> getFollowing(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new GlobalException("User not found"))
                .getFollowing();
    }
}
