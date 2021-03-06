package com.kood.dev.springboot2demo.web;

import com.kood.dev.springboot2demo.config.auth.LoginUser;
import com.kood.dev.springboot2demo.config.auth.dto.SessionUser;
import com.kood.dev.springboot2demo.service.PostsService;
import com.kood.dev.springboot2demo.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(@LoginUser SessionUser loginUser, Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (loginUser != null) model.addAttribute("userName", loginUser.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
