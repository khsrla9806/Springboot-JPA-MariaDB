package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal PrincipalUserDetails principal) {
        List<Image> images = imageService.imageStory(principal.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "이미지 가져오기 성공", images), HttpStatus.OK);
    }
}
