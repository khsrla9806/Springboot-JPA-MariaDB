package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web 설정파일로 작동을 한다.
    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/**") // jsp 파일에서 이 경로가 나오면
                .addResourceLocations("file:///" + uploadFolder) // file.path의 경로를 적용시키겠다.
                .setCachePeriod(3600) // 캐시가 유지되는 시간은 1시간이고
                .resourceChain(true) // 위 설정을 발동시키겠다.
                .addResolver(new PathResourceResolver());

    }
}
