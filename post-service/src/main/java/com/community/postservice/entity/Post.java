package com.community.postservice.entity;

import com.community.commonlib.util.PostType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@Data
public class Post {

    @Id
    private String id;

    private String userId;

    @NotBlank
    private String content;

    private PostType type; // TEXT / IMAGE / VIDEO

    private String mediaUrl; // optional

    private int likeCount = 0;

    private List<Comment> comments = new ArrayList<>();

    private String repostOf; // postId if repost

    private LocalDateTime createdAt = LocalDateTime.now();
}
