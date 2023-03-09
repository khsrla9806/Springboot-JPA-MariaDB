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
    // SecurityConfig에서 사용하기 위해서는 OAuth2UserService 타입으로 만들어야 한다.
    // 그렇기 때문에 DefaultOAuthUserService를 상속받으면 된다.
    // OAuth2DetailService의 역할은 OAuth2 요청이 들어왔을 때, 그 응답 데이터를 받아서 로직을 처리하는 역할을 한다.

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth2 서비스 실행됨");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> userInfo = oAuth2User.getAttributes();

        String username = "facebook_" + (String) userInfo.get("id"); // 페이스북에서 주는 정보를 보면 id 값으로 제공된다.
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 비밀번호는 암호화가 되어야 한다.
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { // OAuth2 요청할 때마다 계속 INSERT되는 것을 막기위한 로직, 기존 회원이 존재하면 회원가입 로직 X
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

            // UserDetails 타입으로 반환해야 Session에 저장하고 할 수 있다.
            // 그래서 PrincipalUserDetail에서 OAuth2User를 상속받게하여 loadUser에서 사용할 수 있도록 해준다.
            return new PrincipalUserDetails(userRepository.save(user), oAuth2User.getAttributes());
        }

        return new PrincipalUserDetails(userEntity, oAuth2User.getAttributes());
    }
}
