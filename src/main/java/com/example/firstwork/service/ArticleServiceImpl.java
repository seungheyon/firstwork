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
// ArticleService 인터페이스의 구현체

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<Article> getArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }


    @Transactional(readOnly = true)
    public Article getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
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
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        if (!requestDto.getPassword().equals(article.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else {
            article.update(requestDto);
            return article.getId() + "번 글이 수정되었습니다.";
        }
    }


    @Transactional
    public String deleteArticle(Long id, String password) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        if (!article.getPassword().equals(password)) {
            return "비밀번호가 일치하지 않습니다.";
        } else {
            articleRepository.deleteById(id);
            return id + "번 글이 삭제되었습니다.";
        }
    }
}
