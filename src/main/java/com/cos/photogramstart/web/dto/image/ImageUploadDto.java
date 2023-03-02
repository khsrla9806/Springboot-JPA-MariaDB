package com.cos.photogramstart.web.dto.image;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {

    // 원래는 photoImageUrl을 받아야 하지만, 우리가 직접 받는것은 Url이 아닌 파일이다.
    // MultipartFile로 받아주면 다양한 확장자의 파일을 받을 수 있다.
    private MultipartFile file;

    private String caption;

    public Image toEntity(String photoImageUrl, User user) {
        return Image.builder()
                .caption(caption)
                .photoImageUrl(photoImageUrl)
                .user(user)
                .build();
    }
}
