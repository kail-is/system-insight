package com.system.insight.user.application.port.in;

import com.system.insight.user.domain.model.User;

public interface UserUseCase {
    User createUser(String userId, String nickname);
    User updateUserNickname(String userId, String newNickname);
    User getUser(String userId);
} 