package com.example.firstwork.controller;

import com.example.firstwork.dto.LoginRequestDto;
import com.example.firstwork.dto.SignupRequestDto;
import com.example.firstwork.entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;
import com.example.firstwork.service.ArticleService;

import javax.servlet.http.HttpServletRequest;

import com.example.firstwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// view 가 아닌 JSON, XML 등의 데이터를 반환하기 위해 사용
@RestController
@RequiredArgsConstructor    // -> 클래스의 필드들을 기반으로 생성자를 자동으로 생성
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;  // 객체 선언

    // 로그인, 회원가입 파트
    @PostMapping("/api/signup") // 회원가입 요청
    public String signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/";
    }
    @PostMapping("/api/login")
    public String login(LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return "redirect:/";
    }



    // @RequestMapping -> 메서드에 적용되는 어노테이션으로, Http 요청이 들어올 때 해당 요청에 따라 메서드를 호출
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