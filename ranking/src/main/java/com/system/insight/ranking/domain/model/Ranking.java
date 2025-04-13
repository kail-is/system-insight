package com.system.insight.ranking.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ranking {
    private final String userId;
    private final String nickname;
    private final String profileImageUrl;
    private final Long score;
    private final Integer ranking;
    private final LocalDateTime snapshotTime;
    
    public static Ranking create(String userId, String nickname, String profileImageUrl, Long score, Integer ranking, LocalDateTime snapshotTime) {
        return new Ranking(userId, nickname, profileImageUrl, score, ranking, snapshotTime);
    }
} 