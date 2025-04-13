package com.system.insight.ranking.domain.port.out;

import com.system.insight.ranking.domain.model.Ranking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RankingRepository {
    Ranking save(Ranking ranking);
    Optional<Ranking> findById(Long id);
    List<Ranking> findBySnapshotTime(LocalDateTime snapshotTime);
    List<Ranking> findByUserIdAndSnapshotTime(String userId, LocalDateTime snapshotTime);
    void delete(Ranking ranking);
} 