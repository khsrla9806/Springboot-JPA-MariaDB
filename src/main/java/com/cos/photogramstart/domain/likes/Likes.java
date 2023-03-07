package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = { // 같은 유저가 계속 좋아요를 누르는 것을 방지하기 위한 제약조건
        @UniqueConstraint(name = "like_uk",
        columnNames = {
            "imageId",
            "userId"
        })
})
public class Likes { // MariaDB에는 Like라는 예약어가 있기 때문에 Likes라고 이름을 생성해야 한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @JoinColumn(name = "imageId")
    @ManyToOne
    private Image image;

    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"}) // images 호출 -> likes 호출 -> user 호출 -> images 호출 : 무한 참조를 막기위함
    @ManyToOne
    private User user;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
