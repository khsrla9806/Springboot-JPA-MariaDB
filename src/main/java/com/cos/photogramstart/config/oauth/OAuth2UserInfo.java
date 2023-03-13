package com.cos.photogramstart.config.oauth;

import java.util.Map;

// OAuth2를 제공하는 회사마다 제공하는 정보가 다르기 때문에 공통 사항을 다룰 추상 클래스 선언
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getUsername();

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
