package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    // 사진 설명
    private String caption;

    // 사진이 저장된 경로
    private String photoImageUrl;

    // 사진을 올린 User
    @JoinColumn(name = "userId")
    @ManyToOne // 한 명의 유저가 여러개의 이미지를 올리 수 있기 때문에
    private User user;

    // 이미지 좋아요

    // 이미지 좋아요 카운팅

    // 댓글 정보

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
