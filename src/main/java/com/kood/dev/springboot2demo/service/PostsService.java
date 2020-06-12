package com.kood.dev.springboot2demo.service;

import com.kood.dev.springboot2demo.domain.posts.PostsRepository;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveDto postsSaveDto) {
        return postsRepository.save(postsSaveDto.toEntity()).getId();
    }


}
