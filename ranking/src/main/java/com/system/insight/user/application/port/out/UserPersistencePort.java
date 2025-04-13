package com.system.insight.user.application.port.out;

import com.system.insight.user.domain.model.User;

public interface UserPersistencePort {
    User save(User user);
    User findByUserId(String userId);
    boolean existsByUserId(String userId);
} 