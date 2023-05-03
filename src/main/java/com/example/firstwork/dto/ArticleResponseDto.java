package com.example.firstwork.dto;

import com.example.firstwork.entity.Article;
import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private String title;
    private String author;
    private String contents;
    //private String password;
    private Long userid;

    public ArticleResponseDto(String title, String author, String contents, Long userid) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        //this.password = password;
        this.userid = userid;
    }

    public ArticleResponseDto(Article article) {
        this.title = article.getTitle();
        this.author = article.getWriterName();
        this.contents = article.getContents();
        //this.password = article.getPassword();
        this.userid = article.getUserId();
    }
}
