package com.system.insight.ranking.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rankings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int ranking;

    @Column(nullable = false)
    private LocalDateTime snapshotTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public RankingJpaEntity(String userId, String nickname, String profileImageUrl, int score, int ranking, LocalDateTime snapshotTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.score = score;
        this.ranking = ranking;
        this.snapshotTime = snapshotTime;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
} 