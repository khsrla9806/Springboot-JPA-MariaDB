package com.cos.photogramstart.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String name; // 별명

    private String website; // 개인 웹사이트 주소

    private String bio; // 자기소개

    private String phone; // 휴대폰 번호

    private String gender; // 성별

    private String profileImageUrl;

    private String role; // 역할

    private LocalDateTime createDate;

    @PrePersist // 데이터가 Insert 되기 전에 실행된다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
