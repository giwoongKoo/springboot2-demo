package com.kood.dev.springboot2demo.web;

import com.kood.dev.springboot2demo.service.PostsService;
import com.kood.dev.springboot2demo.web.dto.PostsResponseDto;
import com.kood.dev.springboot2demo.web.dto.PostsSaveDto;
import com.kood.dev.springboot2demo.web.dto.PostsUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping
    public Long save(@RequestBody PostsSaveDto postsSaveDto) {
        return postsService.save(postsSaveDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateDto postsUpdateDto) {
        return postsService.update(id, postsUpdateDto);
    }

    @GetMapping("/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

}
