package com.example.firstwork.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String contents;

    public CommentResponseDto(String contents) {
        this.contents = contents;
    }
}
