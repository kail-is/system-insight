package com.system.insight.user.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static User create(String username, String email, String password, UserRole role) {
        return new User(username, email, password, role);
    }

    public enum UserRole {
        ADMIN, USER
    }
} 