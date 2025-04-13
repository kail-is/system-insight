package com.system.insight.gamerecord.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameRecordJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "game_type", nullable = false)
    private String gameType;

    @Column(nullable = false)
    private Long score;

    @Column(name = "play_time", nullable = false)
    private Integer playTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public GameRecordJpaEntity(String userId, String gameType, Long score, Integer playTime) {
        this.userId = userId;
        this.gameType = gameType;
        this.score = score;
        this.playTime = playTime;
        this.createdAt = LocalDateTime.now();
    }
} 