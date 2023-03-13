package com.cos.photogramstart.config.oauth;

import java.util.Map;

public class NaverInfo extends OAuth2UserInfo {
    public NaverInfo(Map<String, Object> attributes) {
        super((Map<String, Object>)attributes.get("response")); // Kakao는 attributes -> response 안에 데이터가 들어가 있다.
    }

    @Override
    public String getUsername() {
        return "Naver_" + (String) attributes.get("id");
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return "";
    }
}
