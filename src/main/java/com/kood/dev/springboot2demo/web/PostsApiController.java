package com.kood.dev.springboot2demo.web;

import com.kood.dev.springboot2demo.service.PostsService;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping
    public Long save(@RequestBody PostsSaveDto postsSaveDto) {
        return postsService.save(postsSaveDto);
    }

}
