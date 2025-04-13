package com.system.insight.user.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long id;
    private String userId;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static User create(String userId, String nickname) {
        return new User(userId, nickname);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.updatedAt = LocalDateTime.now();
    }
} 