package com.system.insight.ranking.adapter.out.persistence;

import com.system.insight.ranking.application.port.out.RankingPersistencePort;
import com.system.insight.ranking.domain.model.Ranking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RankingPersistenceAdapter implements RankingPersistencePort {
    private final JpaRankingRepository jpaRepository;
    private final RankingMapper rankingMapper;
    
    @Override
    public Ranking save(Ranking ranking) {
        RankingJpaEntity entity = rankingMapper.toJpaEntity(ranking);
        RankingJpaEntity savedEntity = jpaRepository.save(entity);
        return rankingMapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Ranking> findByUserId(String userId) {
        return jpaRepository.findTopByUserIdOrderBySnapshotTimeDesc(userId)
            .map(rankingMapper::toDomain);
    }
    
    @Override
    public List<Ranking> findTopRankings(int limit, LocalDateTime snapshotTime) {
        return jpaRepository.findTopRankings(limit, snapshotTime).stream()
            .map(rankingMapper::toDomain)
            .toList();
    }
    
    @Override
    public List<Ranking> findUserRankings(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        return jpaRepository.findUserRankings(userId, startTime, endTime).stream()
            .map(rankingMapper::toDomain)
            .toList();
    }
}