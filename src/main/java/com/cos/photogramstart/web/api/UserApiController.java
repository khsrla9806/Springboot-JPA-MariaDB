package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PutMapping("/api/user/{id}") // BindingResult는 반드시 @Valid가 붙는 필드 뒤에 적어줘야 한다.
    public CMRespDto<?> update(@PathVariable int id, @Valid UserUpdateDto dto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalUserDetails principal) {
        // 유효성 검사
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        } else {
            User userEntity = userService.update(id, dto.toEntity());
            principal.setUser(userEntity); // 수정된 세션 정보를 반영
            return new CMRespDto<>(1, "회원수정완료", userEntity);
        }
    }
}
