package com.system.insight.user.application.service;

import com.system.insight.user.application.port.in.UserUseCase;
import com.system.insight.user.application.port.out.UserPersistencePort;
import com.system.insight.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public User createUser(String userId, String nickname, String profileImageUrl) {
        if (userPersistencePort.existsByUserId(userId)) {
            throw new IllegalArgumentException("User already exists with userId: " + userId);
        }
        User user = User.create(userId, nickname, profileImageUrl);
        return userPersistencePort.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        User user = userPersistencePort.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with userId: " + userId);
        }
        return user;
    }

    @Override
    public User findByUserId(String userId) {
        return userPersistencePort.findByUserId(userId);
    }
} 