package com.example.firstwork.controller;

import com.example.firstwork.Entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;
import com.example.firstwork.service.ArticleService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }


    @GetMapping("/api/articles")
    public List<Article> getArticles() {
        return articleService.getArticles();
    }


    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }


    @PostMapping("/api/articles")
    public Article writeArticle(@RequestBody ArticleRequestDto requestDto) {
        return articleService.writeArticle(requestDto);
    }



    @PutMapping("/api/articles/{id}")
    public String updateArticle(@PathVariable Long id, @RequestBody ArticleRequestDto requestDto) {
        return articleService.update(id, requestDto);
    }


    @DeleteMapping("/api/articles/{id}")
    public String deleteArticle(@PathVariable Long id, HttpServletRequest request) {
        String password = request.getParameter("password");
        if (password == null || password.isEmpty()) {
            return "비밀번호를 입력해주세요.";
        }
        return articleService.deleteArticle(id, password);
    }

}