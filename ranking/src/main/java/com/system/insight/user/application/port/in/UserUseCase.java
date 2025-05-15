package com.system.insight.user.application.port.in;

import com.system.insight.user.domain.model.User;

public interface UserUseCase {
    User createUser(String userId, String nickname, String profileImageUrl);
    User getUser(String userId);
    User findByUserId(String userId);
} 