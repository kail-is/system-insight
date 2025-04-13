package com.system.insight.ranking.adapter.out.persistence;

import com.system.insight.ranking.domain.model.Ranking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RankingMapper {
    
    default RankingJpaEntity toJpaEntity(Ranking ranking) {
        return new RankingJpaEntity(
            ranking.getUserId(),
            ranking.getNickname(),
            ranking.getProfileImageUrl(),
            ranking.getScore().intValue(),
            ranking.getRanking(),
            ranking.getSnapshotTime()
        );
    }
    
    default Ranking toDomain(RankingJpaEntity entity) {
        return Ranking.create(
            entity.getUserId(),
            entity.getNickname(),
            entity.getProfileImageUrl(),
            (long) entity.getScore(),
            entity.getRanking(),
            entity.getSnapshotTime()
        );
    }
} 