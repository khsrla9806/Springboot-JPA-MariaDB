package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SiginupDto {
    @Size(min = 3, max = 20) // 필드에 제한 조건을 추가한다 -> validation 체크를 하기 위해서
    private String username;

    @NotBlank // 필드에 제한 조건을 추가한다 -> validation 체크를 하기 위해서
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
