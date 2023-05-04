package com.example.firstwork.entity;

import com.example.firstwork.dto.ArticleNewRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.firstwork.dto.ArticleRequestDto;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // jPA 에서 엔티티 클래스에 선언하는 어노테이션 :: 엔티티 클래스는 DB의 테이블에 매핑되는 데이터
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor  // 매개변수가 없는 기본 생성자를 생성해주는 lombok 어노테이션 :: JPA 에서는 엔티티를 생성할 때, 매개변수가 없는 기본 생성자를 필요로 함
public class Article extends Timestamped {
    @Id // 엔티티 클래스에서 해당 필드가 PK 임을 나타내는 어노테이션
    @GeneratedValue(strategy = GenerationType.AUTO) // 엔티티 클래스의 PK 값을 자동으로 생성
    private Long id;

    @Column(nullable = false)   // 엔티티 클래스의 멤버 변수를 테이블의 컬럼으로 지정
    private String title;

    @Column(nullable = false)
    private String writerName;

    @Column(nullable = false)
    private String contents;

    //@Column(nullable = false)
    //@JsonIgnore // 해당 필드가 데이터 직렬화 또는 역직렬화 과정에서 무시되도록 지정(보안상 중요한 정보를 가진 필드를 노출하지 않기 위해 사용)
    //private String password;

    @Column(nullable = false)
    private Long userId;

//    @CreatedDate
//    private LocalDateTime createdAt;



    // 매개변수가 있는 생성자
    public Article(String title, String writerName, String contents, Long userId) {
        this.title = title;
        this.writerName = writerName;
        this.contents = contents;
        this.userId = userId;
    }

    // 매개변수가 Dto 인 생성자(?)
    public Article(ArticleNewRequestDto requestDto, String userName, Long userID) {
        this.title = requestDto.getTitle();
        this.writerName = userName;
        this.contents = requestDto.getContents();
        this.userId = userID;
    }


    public void update(ArticleNewRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }


}
