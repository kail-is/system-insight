package com.system.insight.gamerecord.adapter.out.persistence;

import com.system.insight.gamerecord.domain.model.GameRecord;
import org.springframework.stereotype.Component;

@Component
public class GameRecordMapper {
    public GameRecordJpaEntity toEntity(GameRecord gameRecord) {
        return new GameRecordJpaEntity(
            gameRecord.getUserId(),
            gameRecord.getGameType(),
            gameRecord.getScore(),
            gameRecord.getPlayTime()
        );
    }

    public GameRecord toDomain(GameRecordJpaEntity entity) {
        return GameRecord.create(
            entity.getUserId(),
            entity.getGameType(),
            entity.getScore(),
            entity.getPlayTime()
        );
    }
} 