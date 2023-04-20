package com.example.firstwork.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.firstwork.dto.ArticleRequestDto;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Article extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writerName;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @JsonIgnore
    private String password;



    public Article(String title, String writerName, String contents, String password) {
        this.title = title;
        this.writerName = writerName;
        this.contents = contents;
        this.password = password;
    }


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
