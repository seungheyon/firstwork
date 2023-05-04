package com.example.firstwork.entity;

import com.example.firstwork.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long articleId;

    @CreatedDate
    private LocalDateTime createdAt;

    public Comment(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.articleId = requestDto.getArticleId();
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
    }
}
