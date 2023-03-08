package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalUserDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> makeComment(@RequestBody CommentDto dto, @AuthenticationPrincipal PrincipalUserDetails principal) {
        Comment comment = commentService.makeComment(dto.getContent(), dto.getImageId(), principal.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 작성 완료", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id) {
        commentService.deleteComment();
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 완료", null), HttpStatus.OK);
    }
}
