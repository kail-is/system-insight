package com.system.insight.ranking.application.port.out;

import com.system.insight.ranking.domain.model.Ranking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RankingPersistencePort {
    Ranking save(Ranking ranking);
    Optional<Ranking> findByUserId(String userId);
    List<Ranking> findTopRankings(int limit, LocalDateTime snapshotTime);
    List<Ranking> findUserRankings(String userId, LocalDateTime startTime, LocalDateTime endTime);
} 