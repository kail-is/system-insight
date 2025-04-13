package com.system.insight.gamerecord.adapter.out.persistence;

import com.system.insight.gamerecord.application.port.out.GameRecordPersistencePort;
import com.system.insight.gamerecord.domain.model.GameRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameRecordPersistenceAdapter implements GameRecordPersistencePort {
    private final GameRecordRepository gameRecordRepository;
    private final GameRecordMapper gameRecordMapper;

    @Override
    public GameRecord save(GameRecord gameRecord) {
        GameRecordJpaEntity entity = gameRecordMapper.toEntity(gameRecord);
        GameRecordJpaEntity savedEntity = gameRecordRepository.save(entity);
        return gameRecordMapper.toDomain(savedEntity);
    }
} 