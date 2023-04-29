package com.example.firstwork.repository;

import com.example.firstwork.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //음식종류 가져오기
    List<Article> findAllByOrderByCreatedAtDesc();  // Spring JPA 에서 제공하는 기본 메서드 -> 특정 엔티티의 모든 레코드를 조회 (생성 일자 순으로 정렬하여)
}
