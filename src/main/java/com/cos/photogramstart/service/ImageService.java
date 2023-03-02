package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    // application.yml에 커스텀으로 지정한 파일이 업로드되는 경로
    @Value("${file.path}")
    private String uploadFolder;

    private final ImageRepository imageRepository;

    @Transactional
    public void imageUpload(ImageUploadDto dto, PrincipalUserDetails principal) {
        // 업로드되는 원본 파일명
        String originalFileName = dto.getFile().getOriginalFilename();

        // 같은 이름의 파일을 다른 유저가 저장할 위험성이 있기 때문에 식별자로 UUID를 사용
        // UUID는 고유성을 확보할 수 있는 ID값을 만들게끔 해주는 기술이다.
        UUID uuid = UUID.randomUUID();

        // 기존 이미지 파일명과 uuid를 이어서 새로운 파일명을 생성
        String imageFileName = uuid + "_" + originalFileName;
        System.out.println(imageFileName);

        // 이미지 저장경로를 지정
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 파일 업로드하기
        try {
            Files.write(imageFilePath, dto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
