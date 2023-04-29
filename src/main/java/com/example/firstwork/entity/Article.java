package com.example.firstwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.firstwork.dto.ArticleRequestDto;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity // jPA ���� ��ƼƼ Ŭ������ �����ϴ� ������̼� :: ��ƼƼ Ŭ������ DB�� ���̺� ���εǴ� ������
@NoArgsConstructor  // �Ű������� ���� �⺻ �����ڸ� �������ִ� lombok ������̼� :: JPA ������ ��ƼƼ�� ������ ��, �Ű������� ���� �⺻ �����ڸ� �ʿ�� ��
public class Article extends Timestamped {
    @Id // ��ƼƼ Ŭ�������� �ش� �ʵ尡 PK ���� ��Ÿ���� ������̼�
    @GeneratedValue(strategy = GenerationType.AUTO) // ��ƼƼ Ŭ������ PK ���� �ڵ����� ����
    private Long id;

    @Column(nullable = false)   // ��ƼƼ Ŭ������ ��� ������ ���̺��� �÷����� ����
    private String title;

    @Column(nullable = false)
    private String writerName;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @JsonIgnore // �ش� �ʵ尡 ������ ����ȭ �Ǵ� ������ȭ �������� ���õǵ��� ����(���Ȼ� �߿��� ������ ���� �ʵ带 �������� �ʱ� ���� ���)
    private String password;


    // �Ű������� �ִ� ������
    public Article(String title, String writerName, String contents, String password) {
        this.title = title;
        this.writerName = writerName;
        this.contents = contents;
        this.password = password;
    }

    // �Ű������� Dto �� ������(?)
    public Article(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writerName = requestDto.getAuthor();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }


    public void update(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writerName = requestDto.getAuthor();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }

}
