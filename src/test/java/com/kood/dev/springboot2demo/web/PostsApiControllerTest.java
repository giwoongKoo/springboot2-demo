package com.kood.dev.springboot2demo.web;

import com.kood.dev.springboot2demo.domain.posts.Posts;
import com.kood.dev.springboot2demo.domain.posts.PostsRepository;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() {
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
        ResponseEntity responseEntity = restTemplate.postForEntity(url, postsSaveDto, Long.class);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        List<Posts> all = postsRepository.findAll();
        Posts posts = all.get(0);
        assertEquals(title, posts.getTitle());
        assertEquals(content, posts.getContent());

    }
}
