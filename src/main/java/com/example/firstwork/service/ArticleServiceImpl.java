package com.example.firstwork.service;

import com.example.firstwork.dto.ArticleNewRequestDto;
import com.example.firstwork.dto.ArticleResponseDto;
import com.example.firstwork.entity.Article;
import com.example.firstwork.dto.ArticleRequestDto;
import com.example.firstwork.entity.User;
import com.example.firstwork.jwt.JwtUtil;
import com.example.firstwork.repository.ArticleRepository;
import com.example.firstwork.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{
// ArticleService 인터페이스의 구현체

    // 의존성 주입
    private final ArticleRepository articleRepository;
    private  final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional(readOnly = true)
    public List<Article> getArticles() {

        // 여기부터 ArticleResponseDto 생성 및 코드 수정
        //List<ArticleResponseDto> list = new ArrayList<>();
        List<Article> articleList;

        articleList = articleRepository.findAllByOrderByCreatedAtDesc();

//        for (Article article : articleList) {
//            list.add(new ArticleResponseDto(article));
//        }
        // Article (entity) 배열로 반환하는 것과 ArticleResponseDto 배열을 만들어서 반환하는 것의 차이?
        // -> ArticleResponseDto 로 반환하면 클라이언트가 Article 엔티티에 종속성을 가지지 않게 하며, 유지보수성이 향상됨!
        return articleList;

        //return articleRepository.findAllByOrderByCreatedAtDesc();
    }


    @Transactional(readOnly = true)
    public Article getArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        return articleRepository.getReferenceById(id);
    }


    @Transactional  // 글 작성 -> 로그인 한 회원만 글 작성 가능
    public Article writeArticle(ArticleNewRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());
            // 작성된 게시글 dB에 저장
            Article article = new Article(requestDto, claims.getSubject(), Long.parseLong(claims.getId()));
            articleRepository.save(article);
            return article;
        }
        else{
            log.info("Token is NULL");
            return null;
        }
    }


    @Transactional
    public String update(Long id, ArticleNewRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());
            // 게시글 update
            Article article = articleRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("글이 존재하지 않습니다.")
            );
            // 토큰 정보의 userID와 수정을 원하는 게시글의 userID가 일치할 경우에만 글 수정 허용
            if(!article.getUserId().equals(Long.parseLong(claims.getId()))){
                throw new IllegalArgumentException("글 작성자만 수정할 수 있습니다");
            }
            else {
                article.update(requestDto);
                return article.getId() + "번 글이 수정되었습니다.";
            }
        }
        else{
            log.info("Token is NULL");
            return null;
        }
    }


    @Transactional
    public String deleteArticle(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());
            // 게시글 delete
            Article article = articleRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("글이 존재하지 않습니다.")
            );
            // 토큰 정보의 userID와 수정을 원하는 게시글의 userID가 일치할 경우에만 글 삭제 허용
            if(!article.getUserId().equals(Long.parseLong(claims.getId()))){
                throw new IllegalArgumentException("글 작성자만 삭제할 수 있습니다");
            }
            else {
                articleRepository.deleteById(id);
                return article.getId() + "번 글이 삭제되었습니다.";
            }
        }
        else{
            log.info("Token is NULL");
            return null;
        }
    }


}
