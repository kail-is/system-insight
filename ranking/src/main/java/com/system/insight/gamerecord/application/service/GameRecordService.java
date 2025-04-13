package com.system.insight.gamerecord.application.service;

import com.system.insight.gamerecord.application.port.in.GameRecordUseCase;
import com.system.insight.gamerecord.application.port.out.GameRecordPersistencePort;
import com.system.insight.gamerecord.domain.model.GameRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameRecordService implements GameRecordUseCase {
    private final GameRecordPersistencePort gameRecordPersistencePort;

    @Override
    public GameRecord recordGame(String userId, String gameType, Long score, Integer playTime) {
        GameRecord gameRecord = GameRecord.create(userId, gameType, score, playTime);
        return gameRecordPersistencePort.save(gameRecord);
    }
} 