package com.kood.dev.springboot2demo.domain.posts;

import com.kood.dev.springboot2demo.domain.posts.Posts;
import com.kood.dev.springboot2demo.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given - 테스트 기반 환경을 구축 (데이터 설정)
        postsRepository.save(Posts.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .build());

        //when - 테스트 하고자 하는 행위
        List<Posts> postsList = postsRepository.findAll();

        //then - 테스트 결과 검증
        Posts posts = postsList.get(0);

        assertEquals("Test title", posts.getTitle());
        assertEquals("Test content", posts.getContent());
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2020, 6, 16, 0, 0, 0);
        postsRepository.save(Posts.builder()
                        .title("게시글 테스트")
                        .content("본문 테스트")
                        .author("등록자 테스트")
                        .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }
}
