package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
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
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.");
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

}
