package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 설정 파일로 등록된 것을 활성화시켜준다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속받으면 시큐리티 설정파일로 인식이 됨

    private final OAuth2DetailService oAuth2DetailService;
    @Bean // 비밀번호 해쉬화에 필요한 엔코더를 스프링빈으로 등록 = 다른 곳에서 DI 받아서 사용할 수 있도록 하기 위함
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티 작동 방식을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable(); // csrf 토큰 사용 비활성화

        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
                .authenticated() // 위에있는 요청이 들어오면 인증이 필요하다.
                .anyRequest() // 인증이 필요하지 않은 요청이 들어오면
                .permitAll() // 모두 허용
                .and()
                .formLogin() // 로그인 인증이 들어오면
                .loginPage("/auth/signin") // 로그인 페이지로 이동을 시키고
                .loginProcessingUrl("/auth/signin") // 스프링 시큐리티에 로그인 Post 요청을 위임함
                .defaultSuccessUrl("/") // 로그인에 성공하면 홈페이지로 이동시켜줘
                .and()
                .oauth2Login() // OAuth2 로그인 요청이 들어오면 처리해준다.
                .userInfoEndpoint() // 요청이 끝나면 인증코드가 아닌 회원정보를 받겠다는 코드
                .userService(oAuth2DetailService); // 해당 로직을 실행시키고, 데이터를 받을 서비스를 지정한다.
    }
}
