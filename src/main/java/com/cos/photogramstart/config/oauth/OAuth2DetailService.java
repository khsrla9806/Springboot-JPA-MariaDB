package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oauth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {
        OAuth2UserInfo oauth2UserInfo = null;

        System.out.println(oauth2User.getAttributes());

        if (userRequest.getClientRegistration().getClientName().equals("Google")) {
            System.out.println("구글 로그인을 요청합니다.");
            oauth2UserInfo = new GoogleInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
            System.out.println("페이스북 로그인을 요청합니다.");
            oauth2UserInfo = new FacebookInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Kakao")) {
            System.out.println("카카오 로그인을 요청합니다.");
            oauth2UserInfo = new KakaoInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Naver")) {
            System.out.println("네이버 로그인을 요청합니다.");
            oauth2UserInfo = new NaverInfo((Map)oauth2User.getAttributes());
        } else {
            System.out.println("구글, 페이스북, 카카오, 네이버만 로그인을 제공합니다.");
        }

        String username = oauth2UserInfo.getUsername();
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String name = oauth2UserInfo.getName();
        String email = oauth2UserInfo.getEmail();

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            User user = User.builder()
                    .username(username)
                    .name(name)
                    .password(password)
                    .email(email)
                    .role("ROLE_USER")
                    .build();

            userEntity = userRepository.save(user);
        }

        return new PrincipalUserDetails(userEntity, oauth2User.getAttributes());
    }
}
