package com.community.userservice.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    @NotBlank
    private String username;

    private Set<String> followers = new HashSet<>();
    private Set<String> following = new HashSet<>();
}