package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SiginupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    // 받아온 DTO의 데이터가 맞는지 Logger 라이브러리를 사용
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    // 로그인 페이지로 이동
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입 기능
    @PostMapping("/auth/signup")
    public String signup(SiginupDto dto) {
        User user = dto.toEntity();
        User userEntity = authService.signup(user);
        log.info(userEntity.toString());
        return "auth/signin";
    }
}
