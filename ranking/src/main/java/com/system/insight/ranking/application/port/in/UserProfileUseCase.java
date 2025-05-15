package com.system.insight.ranking.application.port.in;

import com.system.insight.ranking.adapter.out.dto.UserInfoDto;

public interface UserProfileUseCase {
    UserInfoDto getUserProfile(String userId);
    void saveUserProfile(String userId, UserInfoDto userInfo);
} 