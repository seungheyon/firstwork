package com.example.firstwork.service;


import com.example.firstwork.dto.LoginRequestDto;
import com.example.firstwork.dto.SignupRequestDto;
import com.example.firstwork.entity.User;
import com.example.firstwork.entity.UserRoleEnum;
import com.example.firstwork.jwt.JwtUtil;
import com.example.firstwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        //String email = signupRequestDto.getEmail();

        // username 유효성 검사
        if (!Pattern.matches("^[a-z0-9]{4,10}$", username)) {
            throw new IllegalArgumentException("username은 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다.");
        }

        // password 유효성 검사
        if (!Pattern.matches("^[a-zA-Z0-9]{8,15}$", password)) {
            throw new IllegalArgumentException("password는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성되어야 합니다.");
        }

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);

        found.ifPresent(it -> {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        });

        // 사용자 ROLE 확인 -> 관리자 없이 전부 USER 로 처리
        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User(username, password, role);
        userRepository.save(user);  //db에 회원 저장
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        log.info("user login info = username  : {} , password {}",username,password);
        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // response 객체의 해더에 key(헤더 kwy)값과 value(토큰)값을 넣어준다 -> jwtUtil.createToken()
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole(), String.valueOf(user.getId())));
    }
}