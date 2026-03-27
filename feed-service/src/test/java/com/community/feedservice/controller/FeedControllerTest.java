package com.community.feedservice.controller;

import com.community.feedservice.entity.Feed;
import com.community.feedservice.repository.FeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "eureka.client.enabled=false")
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedRepository feedRepository;
    @Test
    void testGetFeed() throws Exception {
        Feed mockFeed = new Feed();
        mockFeed.setUserId("user1");

        when(feedRepository.findByUserId("user1")).thenReturn(Optional.of(mockFeed));

        // --- EXECUTION ---
        mockMvc.perform(get("/feed/user1"))
                .andExpect(status().isOk());
    }
}