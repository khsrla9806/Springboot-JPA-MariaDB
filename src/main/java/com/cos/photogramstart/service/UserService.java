package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User update(int id, User user) {
        // 영속화 하기
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            throw new CustomValidationApiException("해당 유저를 찾을 수 없습니다.");
        });

        // 영속화된 유저 수정하기
        userEntity.setName(user.getName());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        if (!user.getPassword().equals("")) { // dto에 입력된 비밀번호가 없을 때는 기존 비밀번호 그대로
            // 비밀번호 해쉬화하기
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            userEntity.setPassword(encPassword);
        }
        return userEntity;
    }

    @Transactional(readOnly = true) // 영속성 컨텍스트의 데이터 변경감지를 하지 않기 때문에, 다른 곳에서 회원 프로필을 수정해도 영향을 주지 않는다.
    public UserProfileDto UserProfile(int pageUserId, int principalId) {
        // 페이지 주인 여부와 오브젝트를 받아주기 위한 Dto 생성
        UserProfileDto dto  = new UserProfileDto(); // 아직 영속화되지 않은 상태

        User user = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 유저의 페이지입니다.");
        });

        int subscribeState = subscribeRepository.customSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.customSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        // DTO에 페이지에 접속한 유저와 주인 여부를 담아주기
        dto.setUser(user);
        dto.setPageOwnerState(pageUserId == principalId);
        dto.setImageCount(user.getImages().size());

        user.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User profileImageUrlUpdate(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("찾는 유저는 없습니다.");
        });

        user.setProfileImageUrl(imageFileName);

        return user;
    }

}
