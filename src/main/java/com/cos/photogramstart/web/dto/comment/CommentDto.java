package com.cos.photogramstart.web.dto.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentDto {
    @NotBlank
    private String content;

    @NotNull // Integer, int 타입은 NotBlank 유효성 검사가 불가능하다.
    private int imageId;
}
