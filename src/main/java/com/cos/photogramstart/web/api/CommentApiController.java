package com.cos.photogramstart.web.api;

import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> makeComment() {
        commentService.makeComment();
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 작성 완료", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment();
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 완료", null), HttpStatus.OK);
    }
}
