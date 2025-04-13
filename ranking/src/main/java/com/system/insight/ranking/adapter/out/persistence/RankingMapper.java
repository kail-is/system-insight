package com.system.insight.ranking.adapter.out.persistence;

import com.system.insight.ranking.domain.model.Ranking;
import org.springframework.stereotype.Component;

@Component
public class RankingMapper {
    
    public Ranking toDomain(RankingJpaEntity entity) {
        return Ranking.create(
                entity.getUserId(),
                entity.getNickname(),
                entity.getProfileImageUrl(),
                (long) entity.getScore(),
                entity.getRanking(),
                entity.getSnapshotTime()
        );
    }
    
    public RankingJpaEntity toJpaEntity(Ranking domain) {
        return new RankingJpaEntity(
                domain.getUserId(),
                domain.getNickname(),
                domain.getProfileImageUrl(),
                domain.getScore().intValue(),
                domain.getRanking(),
                domain.getSnapshotTime()
        );
    }
} 