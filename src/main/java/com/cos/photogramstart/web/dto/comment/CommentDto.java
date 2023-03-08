package com.cos.photogramstart.web.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String content;

    private int imageId;
}
