package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void like(int imageId, int principalId) {
        likesRepository.customLike(imageId, principalId);
    }

    @Transactional
    public void cancelLike(int imageId, int principalId) {
        likesRepository.customCancelLike(imageId, principalId);
    }
}
