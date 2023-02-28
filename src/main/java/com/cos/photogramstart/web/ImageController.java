package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {

    // 메인 스토리 페이지로 이동 (로그인 완료시)
    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    // 인기 사진 페이지로 이동
    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    // 이미지 업로드하는 페이지로 이동
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }
}
