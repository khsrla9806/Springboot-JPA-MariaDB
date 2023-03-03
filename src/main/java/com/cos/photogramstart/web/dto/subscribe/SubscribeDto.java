package com.cos.photogramstart.web.dto.subscribe;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeDto {

    // 로그인한 유저가 모달에서 확인할 유저의 Id
    private int id;

    // 로그인한 유저가 모달에서 확인할 유저의 이름
    private String username;

    // 로그인한 유저가 모달에서 확인할 유저의 프로필 이미지
    private String profileImageUrl;

    // 로그인한 유저가 모달에서 확인할 유저를 구독했는지 여부
    private Integer subscribeState;

    // 로그인한 유저가 모달에서 확인하는 유저가 본인인지 여부
    private Integer equalUserState;
}
