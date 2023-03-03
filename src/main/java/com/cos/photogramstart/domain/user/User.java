package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(unique = true, length = 20, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name; // 별명

    private String website; // 개인 웹사이트 주소

    private String bio; // 자기소개

    private String phone; // 휴대폰 번호

    private String gender; // 성별

    private String profileImageUrl;

    private String role; // 역할


    // image와 user의 양방향성 연관관계를 매핑하는 작업
    // 이 연관관계의 주인은 user가 아니니, user 테이블을 만들 때 image 테이블을 만들지는 않을 거다.
    // User를 Select할 때, 해당 user와 연관된 image들을 모두 가져올 것이다.
    // EAGER: User를 가져올 때, 해당 userId로 등록된 모든 images를 가져오겠다.
    // LAZY: User를 가져올 때, 해당 userId로 등록된 모든 images를 가져오지 않겠다.
    // 우리는 LAZY를 사용한다. LAZY의 또다른 기능은 getImages()가 작동할 때만 images를 가져올 수 있게 된다.
    // 이유는 양방향성 매핑을 했기 때문에 user 호출 -> image -> image에 있는 user가 호출됨 -> image 이런식으로 무한적인 참조가 발생
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // Image 오브젝트 내부에 있는 user는 Json으로 파싱하지 않겠다. (무한참조 방지)
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist // 데이터가 Insert 되기 전에 실행된다.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
