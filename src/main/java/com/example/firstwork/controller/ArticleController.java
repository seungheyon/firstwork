package com.example.firstwork.controller;

import com.example.firstwork.dto.*;
import com.example.firstwork.entity.Article;
import com.example.firstwork.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.firstwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// view 가 아닌 JSON, XML 등의 데이터를 반환하 기 위해 사용
//@RestController
@Controller
@Slf4j
@RequiredArgsConstructor    // -> 클래스의 필드들을 기반으로 생성자를 자동으로 생성
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;  // 객체 선언

    // 로그인, 회원가입 파트
    @ResponseBody
    @PostMapping("/api/signup") // 회원가입 요청
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("signupRequestDto = {} ",signupRequestDto);
        userService.signup(signupRequestDto);
        return "redirect:/";
    }
    @ResponseBody
    @PostMapping("/api/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }
    // @RequestMapping -> 메서드에 적용되는 어노테이션으로, Http 요청이 들어올 때 해당 요청에 따라 메서드를 호출
    @GetMapping("/a")
        public ModelAndView home() {
            log.info("home");
            return new ModelAndView("index2");
    }
    // ================= 로그인, 회원가입 및 API 실험 끝 ===================



    @ResponseBody
    @GetMapping("/api/articles")    // 전체 게시글의 제목, 작성자, 내용, 작성 날짜 조회 (-> 작성 날짜 순으로 내림차순)
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @ResponseBody
    @GetMapping("/api/articles/{id}")   // id 로 선택한 게시글의 제목, 작성자, 내용 , 작성 날짜 조회
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @ResponseBody
    @PostMapping("/api/articles")   // 게시글 작성(로그인한 회원에 한해 작성)
    public Article writeArticle(@RequestBody ArticleNewRequestDto requestDto, HttpServletRequest request) {  // Token 값을 받아와야 하기 때문에 HttpServletRequest 사용
        return articleService.writeArticle(requestDto, request);
    }

    @ResponseBody
    @PutMapping("/api/articles/{id}")   // 게시글 수정(글을 작성한 회원에 한해 수정)
    public String updateArticle(@PathVariable Long id, @RequestBody ArticleNewRequestDto requestDto, HttpServletRequest request) {
        return articleService.update(id, requestDto, request);
    }

    @ResponseBody
    @DeleteMapping("/api/articles/{id}")   // 게시글 삭제(글을 작성한 회원에 한해 삭제)
    public String deleteArticle(@PathVariable Long id, HttpServletRequest request) {
        return articleService.deleteArticle(id, request);
    }

}