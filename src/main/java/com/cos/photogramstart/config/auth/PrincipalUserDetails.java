package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalUserDetails implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 1L;

    private User user;
    private Map<String, Object> attributes;

    public PrincipalUserDetails(User user) {
        this.user = user;
    }

    // OAuth2 유저 구분을 위한 생성자 오버로딩
    public PrincipalUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    // 사용자의 권한을 가져오는 메서드 (권한이 하나가 아닐 수도 있기에 Collection으로 반환)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 GrantedAuthority 타입으로 받아주기 위한 Collection 선언
        Collection<GrantedAuthority> collectors = new ArrayList<>();

        // 비어있는 권한을 채워준다 (람다식 사용)
        collectors.add(() -> {
            return user.getRole();
        });

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는가? => true (응, 만료되지 않았어)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않는가? => true (응, 안 잠겨있어)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호를 바꾼지 오래되지 않았는가? => true (응, 오래되지 않았어)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 지금 활성화되어 있는가? => true (응, 활성화되어있어)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
