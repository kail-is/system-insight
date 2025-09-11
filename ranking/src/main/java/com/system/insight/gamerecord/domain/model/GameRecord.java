package com.system.insight.gamerecord.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameRecord {
    private Long id;
    private String userId;
    private Long score;
    private LocalDateTime playAt;
    private LocalDateTime createdAt;

    private GameRecord(String userId, Long score, LocalDateTime playAt) {
        this.userId = userId;
        this.score = score;
        this.playAt = playAt;
        this.createdAt = LocalDateTime.now();
    }

    public static GameRecord create(String userId, Long score, LocalDateTime playAt) {
        return new GameRecord(userId, score, playAt);
    }
} 