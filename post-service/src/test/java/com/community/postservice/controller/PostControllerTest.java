package com.community.postservice.controller;

import com.community.postservice.EventPublisher;
import com.community.postservice.entity.Post;
import com.community.postservice.repo.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "eureka.client.enabled=false")
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    //  NEW (IMPORTANT FIX)
    @MockBean
    private EventPublisher eventPublisher;

    @Test
    void testCreatePost() throws Exception {

        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> {
                    Post p = invocation.getArgument(0);
                    p.setId("123");
                    return p;
                });

        //  Prevent Kafka call
        doNothing().when(eventPublisher)
                .publishPostCreated(any(), any(), any());

        String json = """
        {
          "userId": "1",
          "content": "Hello World",
          "type": "TEXT"
        }
        """;

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    //  NEW (like test)
    @Test
    void testLikePost() throws Exception {

        Post post = new Post();
        post.setId("1");

        when(postRepository.findById("1")).thenReturn(Optional.of(post));

        mockMvc.perform(post("/posts/1/like"))
                .andExpect(status().isOk());
    }
}