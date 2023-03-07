package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
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
    @JsonIgnoreProperties({"images"})
    @ManyToOne // 한 명의 유저가 여러개의 이미지를 올리 수 있기 때문에
    private User user;

    // 이미지 좋아요
    @OneToMany(mappedBy = "image") // 양방향성 매핑 (image를 가져올 때 likes도 가져오기 위해서)
    @JsonIgnoreProperties({"image"}) // likes를 호출했을 때, Likes에 있는 image는 다시 호출하지 않겠다. (무한참조 방지)
    private List<Likes> likes;

    // 이미지 좋아요 여부 상태
    @Transient // DB에는 해당 컬럼을 만들지 않을 때 사용하는 어노테이션
    private boolean likeState;

    // 이미지 좋아요 카운팅
    @Transient
    private Integer likeCount;

    // 댓글 정보

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
