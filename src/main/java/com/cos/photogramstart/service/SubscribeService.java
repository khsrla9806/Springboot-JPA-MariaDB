package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    private final EntityManager entityManager; // 서비스에서 네이티브 쿼리를 작성하기 위해서 DI 받아줌

    @Transactional
    public List<SubscribeDto> subscribeList(int principalId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId=? AND toUserId=u.id),1,0) subscribeState, ");
        sb.append("if((?=u.id),1,0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?");

        Query query = entityManager.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

        return subscribeDtos;
    }

    @Transactional
    public void subscribe(int fromUserId, int toUserId) {
        try {
            subscribeRepository.customSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독한 사용자 입니다.");
        }
    }

    @Transactional
    public void unSubscribe(int fromUserId, int toUserId) {
        subscribeRepository.customUnSubscribe(fromUserId, toUserId);
    }
}
