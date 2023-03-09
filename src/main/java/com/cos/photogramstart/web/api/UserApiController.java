package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{id}") // BindingResult는 반드시 @Valid가 붙는 필드 뒤에 적어줘야 한다.
    public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto dto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalUserDetails principal) {
        // 핵심 로직 : 공통 로직은 AOP로 관리
        User userEntity = userService.update(id, dto.toEntity());
        principal.setUser(userEntity); // 수정된 세션 정보를 반영
        return new CMRespDto<>(1, "회원수정완료", userEntity);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Integer pageUserId, @AuthenticationPrincipal PrincipalUserDetails principal, Model model) {
        List<SubscribeDto> subscribeDtos = subscribeService.subscribeList(principal.getUser().getId(), pageUserId);

        return new ResponseEntity<>(new CMRespDto<>(1, "구독정보가져오기 성공", subscribeDtos), HttpStatus.OK);
    }

    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId,
                                                   MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalUserDetails principal) {
        User userEntity = userService.profileImageUrlUpdate(principalId, profileImageFile);

        // 세션을 변경하기 위한 코드
        principal.setUser(userEntity);
        return new ResponseEntity<>(new CMRespDto<>(1, "프로필 사진 변경 완료", null), HttpStatus.OK);
    }
}
