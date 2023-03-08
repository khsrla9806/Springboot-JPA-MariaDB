package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    @Transactional
    public Comment makeComment(String content, int imageId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomApiException("찾는 유저는 존재하지 않습니다.");
        });

        Image image = new Image();
        image.setId(imageId);

        Comment comment = Comment.builder()
                .user(user)
                .image(image)
                .content(content)
                .build();

        // Native Query를 사용할 때, 반환 타입을 void, Integer, int로만 사용할 수 있다.
        // 해당 객체를 반환받아야 한다면 어쩔 수 없기 JPA의 save() 메소드를 써야 한다.
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(int commentId) {
        try {
            commentRepository.deleteById(commentId);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
