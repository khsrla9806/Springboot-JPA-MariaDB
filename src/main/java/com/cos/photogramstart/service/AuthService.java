package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder; // DI 받아서 사용

    @Transactional
    public User signup(User user) {
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");

        User findUsername = userRepository.findByUsername(user.getUsername());

        if (findUsername == null) { // 겹치는 유저가 없으면 정상적으로 회원가입 진행
            User userEntiy = userRepository.save(user);
            return userEntiy;
        } else {
            throw new CustomValidationException(user.getUsername() +"은 이미 존재하는 아이디입니다.", null);
        }
    }
}
