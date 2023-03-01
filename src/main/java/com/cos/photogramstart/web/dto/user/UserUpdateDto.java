package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

@Data
public class UserUpdateDto {
    // username과 email은 수정 불가
    private String name;
    private String password;

    private String website;
    private String bio;
    private String phone;
    private String gender;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }

}
