package com.system.insight.ranking.application.service;

import com.system.insight.ranking.adapter.out.dto.UserInfoDto;
import com.system.insight.ranking.application.port.in.UserProfileUseCase;
import com.system.insight.ranking.application.port.out.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService implements UserProfileUseCase {
    
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional(readOnly = true)
    public UserInfoDto getUserProfile(String userId) {
        return userProfileRepository.findById(userId);
    }

    @Override
    public void saveUserProfile(String userId, UserInfoDto userInfo) {
        userProfileRepository.save(userId, userInfo);
    }
} 