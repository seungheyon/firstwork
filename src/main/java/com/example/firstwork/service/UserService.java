package com.example.firstwork.service;


import com.example.firstwork.dto.LoginRequestDto;
import com.example.firstwork.dto.SignupRequestDto;
import com.example.firstwork.entity.User;
import com.example.firstwork.entity.UserRoleEnum;
import com.example.firstwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        //String email = signupRequestDto.getEmail();

        // ȸ�� �ߺ� Ȯ��
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("�ߺ��� ����ڰ� �����մϴ�.");
        }

        // ����� ROLE Ȯ�� -> ������ ���� ���� USER �� ó��
        UserRoleEnum role = UserRoleEnum.USER;
//        if (signupRequestDto.isAdmin()) {
//            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("������ ��ȣ�� Ʋ�� ����� �Ұ����մϴ�.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User(username, password, role);
        userRepository.save(user);  //db�� ȸ�� ����
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // ����� Ȯ��
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("��ϵ� ����ڰ� �����ϴ�.")
        );

        // ��й�ȣ Ȯ��
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
        }
    }
}