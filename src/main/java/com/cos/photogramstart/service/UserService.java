package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User update(int id, User user) {
        // 영속화 하기
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomValidationApiException("해당 유저를 찾을 수 없습니다.");
        });

        // 비밀번호 해쉬화하기
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);

        // 영속화된 유저 수정하기
        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }

    @Transactional(readOnly = true) // 영속성 컨텍스트의 데이터 변경감지를 하지 않기 때문에, 다른 곳에서 회원 프로필을 수정해도 영향을 주지 않는다.
    public User UserProfile(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 유저의 페이지입니다.");
        });

        return user;
    }

}
