package com.example.firstwork.service;

import com.example.firstwork.entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;

import java.util.List;

public interface ArticleService {
    public List<Article> getArticles();
    public Article getArticle(Long id);
    public Article writeArticle(ArticleRequestDto requestDto);
    public String update(Long id, ArticleRequestDto requestDto);
    public String deleteArticle(Long id, String password);
}
