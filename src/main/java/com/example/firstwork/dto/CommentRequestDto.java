package com.example.firstwork.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String contents;
    private Long articleId;
}
