package com.kood.dev.springboot2demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kood.dev.springboot2demo.domain.posts.Posts;
import com.kood.dev.springboot2demo.domain.posts.PostsRepository;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import com.kood.dev.springboot2demo.web.dto.PostsUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록_테스트() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveDto postsSaveDto = PostsSaveDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
//        ResponseEntity responseEntity = restTemplate.postForEntity(url, postsSaveDto, Long.class);
        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(postsSaveDto)))
            .andExpect(status().isOk());

        //then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);
        assertEquals(title, posts.getTitle());
        assertEquals(content, posts.getContent());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정_테스트() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateDto updateDto = PostsUpdateDto.builder().title(expectedTitle).content(expectedContent).build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

//        HttpEntity<PostsUpdateDto> updateEntity = new HttpEntity<>(updateDto);

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, updateEntity, Long.class);
        mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateDto)))
            .andExpect(status().isOk());

        //then
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);
        assertEquals(expectedTitle, posts.getTitle());
        assertEquals(expectedContent, posts.getContent());
    }
}
