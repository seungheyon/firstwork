package com.example.firstwork.dto;

import lombok.Getter;

@Getter // Getter 메서드를 자동으로 생성 :: 다른 클래스에서 private 멤버 변수에 접근할 수 있도록 하는 메서드
public class ArticleRequestDto {
    private String title;
    private String author;
    private String contents;
    private String password;
}