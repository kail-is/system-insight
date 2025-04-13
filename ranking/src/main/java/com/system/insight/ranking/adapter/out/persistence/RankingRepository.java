package com.system.insight.ranking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RankingRepository extends JpaRepository<RankingJpaEntity, Long> {
    @Query("SELECT r FROM RankingJpaEntity r WHERE r.snapshotTime = :snapshotTime ORDER BY r.ranking ASC")
    List<RankingJpaEntity> findTopRankings(@Param("snapshotTime") LocalDateTime snapshotTime);

    @Query("SELECT r FROM RankingJpaEntity r WHERE r.userId = :userId AND r.snapshotTime BETWEEN :startTime AND :endTime ORDER BY r.snapshotTime ASC")
    List<RankingJpaEntity> findUserRankings(@Param("userId") String userId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);
} 