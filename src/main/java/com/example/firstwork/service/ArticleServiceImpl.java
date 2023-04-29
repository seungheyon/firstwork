package com.example.firstwork.service;

import com.example.firstwork.entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;
import com.example.firstwork.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{
// ArticleService �������̽��� ����ü

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<Article> getArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }


    @Transactional(readOnly = true)
    public Article getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("���� �������� �ʽ��ϴ�.")
        );
        return articleRepository.getReferenceById(id);
    }


    @Transactional
    public Article writeArticle(ArticleRequestDto requestDto) {
            requestDto.getAuthor();
        Article article = new Article(requestDto);
        articleRepository.save(article);
        return article;
    }


    @Transactional
    public String update(Long id, ArticleRequestDto requestDto) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("���� �������� �ʽ��ϴ�.")
        );
        if (!requestDto.getPassword().equals(article.getPassword())) {
            throw new IllegalArgumentException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
        } else {
            article.update(requestDto);
            return article.getId() + "�� ���� �����Ǿ����ϴ�.";
        }
    }


    @Transactional
    public String deleteArticle(Long id, String password) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("���� �������� �ʽ��ϴ�.")
        );
        if (!article.getPassword().equals(password)) {
            return "��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
        } else {
            articleRepository.deleteById(id);
            return id + "�� ���� �����Ǿ����ϴ�.";
        }
    }
}
