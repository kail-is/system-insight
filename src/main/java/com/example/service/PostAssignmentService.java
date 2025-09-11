package com.example.service;

import com.example.dto.AssignmentRequest;
import com.example.entity.PostAssignment;
import com.example.mapper.PostAssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostAssignmentService {

    private final SqlSession sqlSession;

    @Transactional
    public void syncAssignments(String postId, List<AssignmentRequest> requestList) {
        PostAssignmentMapper mapper = sqlSession.getMapper(PostAssignmentMapper.class);

        // 1. 입력값 가공
        Map<String, AssignmentRequest> incomingMap = requestList.stream()
                .collect(Collectors.toMap(AssignmentRequest::getName, r -> r));

        String newManagerName = requestList.stream()
                .filter(r -> "Y".equals(r.getIsManager()))
                .map(AssignmentRequest::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("담당자는 한 명이어야 합니다."));

        // 2. 기존 데이터 조회
        List<PostAssignment> existingList = mapper.selectByPostId(postId);

        Set<String> existingNames = existingList.stream()
                .map(PostAssignment::getName)
                .collect(Collectors.toSet());

        int maxSeq = existingList.stream()
                .mapToInt(PostAssignment::getSequence)
                .max()
                .orElse(0);

        // 3. 기존 데이터 갱신 or 삭제
        for (PostAssignment existing : existingList) {
            AssignmentRequest incoming = incomingMap.get(existing.getName());

            existing.setIsManager(existing.getName().equals(newManagerName) ? "Y" : "N");

            if (incoming != null) {
                existing.setIsDeleted("N");
                mapper.update(existing);
            } else {
                existing.setIsDeleted("Y");
                mapper.update(existing);
            }
        }

        // 4. 신규 데이터 삽입
        for (AssignmentRequest request : requestList) {
            if (!existingNames.contains(request.getName())) {
                mapper.insert(PostAssignment.builder()
                        .postId(postId)
                        .sequence(++maxSeq)
                        .name(request.getName())
                        .isManager(request.getIsManager())
                        .isDeleted("N")
                        .build());
            }
        }
    }
} 