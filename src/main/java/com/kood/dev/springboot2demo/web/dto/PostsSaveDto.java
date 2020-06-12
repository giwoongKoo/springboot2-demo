package com.kood.dev.springboot2demo.web.dto;

import com.kood.dev.springboot2demo.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveDto(String title, String content, String author) {
       this.title = title;
       this.content = content;
       this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

    }
}
