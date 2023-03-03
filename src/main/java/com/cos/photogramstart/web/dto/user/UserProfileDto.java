package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    // 이 페이지 주인 여부 [true : 주인] [false : 주인 x]
    private boolean pageOwnerState;

    // 접속한 유저 정보를 받을 수 있는 오브젝트
    private User user;

    // 해당 유저의 게시글 개수
    private int imageCount;

    // 구독상태 내가 구독하고 있는 사람이면 true, 아니면 false
    private boolean subscribeState;

    // 구독자수
    private int subscribeCount;
}
