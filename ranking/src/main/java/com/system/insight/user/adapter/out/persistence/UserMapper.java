package com.system.insight.user.adapter.out.persistence;

import com.system.insight.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(user.getUserId(), user.getNickname(), user.getProfileImageUrl());
    }

    public User toDomain(UserJpaEntity entity) {
        return User.create(entity.getUserId(), entity.getNickname(), entity.getProfileImageUrl());
    }
} 