package com.cos.photogramstart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 시큐리티 설정 파일로 등록된 것을 활성화시켜준다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속받으면 시큐리티 설정파일로 인식이 됨

    // 시큐리티 작동 방식을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**")
                .authenticated() // 위에있는 요청이 들어오면 인증이 필요하다.
                .anyRequest() // 인증이 필요하지 않은 요청이 들어오면
                .permitAll(); // 모두 허용
    }
}
