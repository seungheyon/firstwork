package com.example.firstwork.service;

import com.example.firstwork.dto.ArticleNewRequestDto;
import com.example.firstwork.dto.CommentRequestDto;
import com.example.firstwork.dto.CommentResponseDto;
import com.example.firstwork.entity.Article;
import com.example.firstwork.entity.Comment;
import com.example.firstwork.entity.User;
import com.example.firstwork.entity.UserRoleEnum;
import com.example.firstwork.jwt.JwtUtil;
import com.example.firstwork.repository.ArticleRepository;
import com.example.firstwork.repository.CommentRepository;
import com.example.firstwork.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    // 의존성 주입
    private final ArticleRepository articleRepository;
    private  final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    @Transactional  // 글 작성 -> 로그인 한 회원만 글 작성 가능
    public CommentResponseDto writeComment(CommentRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findById(Long.parseLong(claims.getId())).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());
            // 작성된 게시글 dB에 저장
            Article article = articleRepository.findById(requestDto.getArticleId()).orElseThrow(
                    () -> new IllegalArgumentException("글이 존재하지 않습니다.")
            );

            Comment comment = new Comment(requestDto);
            commentRepository.save(comment);    //   댓글을 db에 저장
            //article.addComment(comment);    // 댓글과 게시글을 연결

            return new CommentResponseDto(comment.getContents());
        }
        else{
            log.info("Token is NULL");
            return null;
        }
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findById(Long.parseLong(claims.getId())).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());

            // 작성된 게시글 dB에 저장
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            if(user.getRole().equals(UserRoleEnum.ADMIN)){  // User 권한이 ADMIN 인지 확인
                comment.update(requestDto);
                return new CommentResponseDto(comment.getContents());
            }
            else{   // User 권한이 ADMIN 이 아닐 경우 -> 글 작성자게 한해서만 수정 가능
                if(!comment.getArticleId().equals(Long.parseLong(claims.getId()))){
                    throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
                }
                else {  // 토큰 정보의 userID와 수정을 원하는 게시글의 userID가 일치할 경우에만 글 수정 허용
                    comment.update(requestDto);
                    return new CommentResponseDto(comment.getContents());
                } //pt test
            }
        }
        else{
            log.info("Token is NULL");
            return null;
        }
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long commentId, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;  // 사용자 정보를 담는 그릇(?)

        if(token !=null){   // request 에 Token 이 있으면
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findById(Long.parseLong(claims.getId())).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            log.info("claims.subject  : {} , claims.getId {}",claims.getSubject(),claims.getId());

            // 작성된 게시글 dB에 저장
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            if(user.getRole().equals(UserRoleEnum.ADMIN)){  // User 권한이 ADMIN 인지 확인
                commentRepository.deleteById(commentId);
                return ResponseEntity.ok("Success");
            }
            else{   // User 권한이 ADMIN 이 아닐 경우 -> 글 작성자게 한해서만 수정 가능
                if(!comment.getArticleId().equals(Long.parseLong(claims.getId()))){
                    throw new IllegalArgumentException("작성자만 삭제할 수 있습니다");
                }
                else {  // 토큰 정보의 userID와 수정을 원하는 게시글의 userID가 일치할 경우에만 글 수정 허용
                    commentRepository.deleteById(commentId);
                    return ResponseEntity.ok("Success");
                }
            }
        }
        else{
            log.info("Token is NULL");
            return ResponseEntity.notFound().build();
        }
    }

}
