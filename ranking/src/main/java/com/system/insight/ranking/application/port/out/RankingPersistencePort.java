package com.system.insight.ranking.application.port.out;

import com.system.insight.ranking.domain.model.Ranking;

import java.time.LocalDateTime;
import java.util.List;

public interface RankingPersistencePort {
    Ranking save(Ranking ranking);
    List<Ranking> findTopRankings(int limit, LocalDateTime snapshotTime);
    List<Ranking> findUserRankings(String userId, LocalDateTime startTime, LocalDateTime endTime);
} 