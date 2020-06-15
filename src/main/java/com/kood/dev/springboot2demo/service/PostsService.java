package com.kood.dev.springboot2demo.service;

import com.kood.dev.springboot2demo.domain.posts.Posts;
import com.kood.dev.springboot2demo.domain.posts.PostsRepository;
import com.kood.dev.springboot2demo.web.dto.PostsListResponseDto;
import com.kood.dev.springboot2demo.web.dto.PostsResponseDto;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import com.kood.dev.springboot2demo.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveDto postsSaveDto) {
        return postsRepository.save(postsSaveDto.toEntity()).getId();
    }


    @Transactional
    public Long update(Long id, PostsUpdateDto postsUpdateDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found posts. id : " + id));
        posts.update(postsUpdateDto.getTitle(), postsUpdateDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found posts. id : " + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found posts. id : " + id));
        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
