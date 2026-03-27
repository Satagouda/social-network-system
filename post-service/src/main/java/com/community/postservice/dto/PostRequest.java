package com.community.postservice.dto;

import com.community.commonlib.util.PostType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String userId;

    private String content;

    private PostType type;

    private String mediaUrl;

}
