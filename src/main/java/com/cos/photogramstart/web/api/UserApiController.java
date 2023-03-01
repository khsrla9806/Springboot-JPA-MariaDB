package com.cos.photogramstart.web.api;

import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PutMapping("/api/user/{id}")
    public String update(UserUpdateDto dto) {
        System.out.println(dto);
        return "수정";
    };
}
