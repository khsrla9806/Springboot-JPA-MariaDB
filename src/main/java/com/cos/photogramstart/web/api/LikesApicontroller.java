package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesApicontroller {
    private final LikesService likesService;

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> like(@PathVariable int imageId,
                                  @AuthenticationPrincipal PrincipalUserDetails principal) {
        likesService.like(imageId, principal.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> cancelLike(@PathVariable int imageId,
                                        @AuthenticationPrincipal PrincipalUserDetails principal) {
        likesService.cancelLike(imageId, principal.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
    }
}
