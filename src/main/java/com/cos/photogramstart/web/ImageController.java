package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    // 메인 스토리 페이지로 이동 (로그인 완료시)
    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    // 인기 사진 페이지로 이동 => 좋아요가 존재하는 페이지들을 랜더링 (좋아요 카운트가 많은 순서대로)
    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<Image> images = imageService.popularImages();
        model.addAttribute("images", images);
        return "image/popular";
    }

    // 이미지 업로드하는 페이지로 이동
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    // 이미지 업로드
    @PostMapping("/image")
    public String imageUpload(ImageUploadDto dto, @AuthenticationPrincipal PrincipalUserDetails principal) {
        if (dto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        
        imageService.imageUpload(dto, principal);

        return "redirect:/user/" + principal.getUser().getId(); // 업로드가 끝나면 해당 유저의 프로필로 다시 이동
    }
}
