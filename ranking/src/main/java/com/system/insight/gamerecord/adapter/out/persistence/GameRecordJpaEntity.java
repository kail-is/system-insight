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

    @Column(nullable = false)
    private Long score;

    @Column(name = "play_at", nullable = false)
    private LocalDateTime playAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public GameRecordJpaEntity(String userId, Long score, LocalDateTime playAt) {
        this.userId = userId;
        this.score = score;
        this.playAt = playAt;
        this.createdAt = LocalDateTime.now();
    }
} 