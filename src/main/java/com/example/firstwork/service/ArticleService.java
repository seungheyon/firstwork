package com.example.firstwork.service;

import com.example.firstwork.dto.ArticleNewRequestDto;
import com.example.firstwork.dto.ArticleResponseDto;
import com.example.firstwork.entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ArticleService {
    public List<ArticleResponseDto> getArticles();
    public ArticleResponseDto getArticle(Long id);
    public Article writeArticle(ArticleNewRequestDto requestDto, HttpServletRequest request);
    public String update(Long id, ArticleNewRequestDto requestDto, HttpServletRequest request);
    public String deleteArticle(Long id, HttpServletRequest request);
}
