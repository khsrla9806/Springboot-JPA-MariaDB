package com.cos.photogramstart.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2DetailService extends DefaultOAuth2UserService {
    // SecurityConfig에서 사용하기 위해서는 OAuth2UserService 타입으로 만들어야 한다.
    // 그렇기 때문에 DefaultOAuthUserService를 상속받으면 된다.
    // OAuth2DetailService의 역할은 OAuth2 요청이 들어왔을 때, 그 응답 데이터를 받아서 로직을 처리하는 역할을 한다.

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth2 서비스 실행됨");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);
        return null;
    }
}
