package com.example.firstwork.dto;

import lombok.Getter;

@Getter // Getter �޼��带 �ڵ����� ���� :: �ٸ� Ŭ�������� private ��� ������ ������ �� �ֵ��� �ϴ� �޼���
public class ArticleRequestDto {
    private String title;
    private String author;
    private String contents;
    private String password;
}