package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data
public class SiginupDto {
    private String username;
    private String password;
    private String email;
    private String name;
}
