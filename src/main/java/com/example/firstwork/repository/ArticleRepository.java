package com.example.firstwork.repository;

import com.example.firstwork.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //�������� ��������
    List<Article> findAllByOrderByCreatedAtDesc();  // Spring JPA ���� �����ϴ� �⺻ �޼��� -> Ư�� ��ƼƼ�� ��� ���ڵ带 ��ȸ (���� ���� ������ �����Ͽ�)
}
