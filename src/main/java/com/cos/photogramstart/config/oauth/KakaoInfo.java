package com.cos.photogramstart.config.oauth;

import java.util.Map;

public class KakaoInfo extends OAuth2UserInfo{
    private Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    private Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
    public KakaoInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return "Kakao_" + attributes.get("id");
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) profile.get("profile_image_url");
    }
}
