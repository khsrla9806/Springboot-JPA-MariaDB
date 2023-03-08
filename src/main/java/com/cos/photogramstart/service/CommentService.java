package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void makeComment() {

    }

    @Transactional
    public void deleteComment() {

    }
}
