package com.system.insight.ranking.application.port.out;

import com.system.insight.ranking.adapter.out.dto.UserInfoDto;

public interface UserProfileRepository {
    UserInfoDto findById(String userId);
    void save(String userId, UserInfoDto userInfo);
} 