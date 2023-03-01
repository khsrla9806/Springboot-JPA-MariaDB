package com.cos.photogramstart.domain.subscribe;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// User(N)와 User(N)의 구독관계를 만들어줄 중간 테이블이다.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table( // 다중 필드에 제약조건을 걸 때는 @Table 어노테이션을 사용해야 한다.
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk", // Unique 제약조건의 이름
                        columnNames = {  // Unique 제약조건을 적용할 컬럼명
                                "fromUserId",
                                "toUserId"
                        }
                )
        }
)
public class Subscribe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne // 1명의 유저와 매칭이 되기 때문에 ManyToOne의 관계이다.
    private User fromUser; // 구독을 하는 사람

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser; // 구독을 받는 사람

    @JoinColumn(name = "createDate")
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
