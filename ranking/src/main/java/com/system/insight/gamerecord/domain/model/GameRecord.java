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
    private String gameType;
    private Long score;
    private Integer playTime;
    private LocalDateTime createdAt;

    private GameRecord(String userId, String gameType, Long score, Integer playTime) {
        this.userId = userId;
        this.gameType = gameType;
        this.score = score;
        this.playTime = playTime;
        this.createdAt = LocalDateTime.now();
    }

    public static GameRecord create(String userId, String gameType, Long score, Integer playTime) {
        return new GameRecord(userId, gameType, score, playTime);
    }
} 