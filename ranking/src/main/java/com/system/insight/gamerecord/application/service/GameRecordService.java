package com.system.insight.gamerecord.application.service;

import com.system.insight.gamerecord.application.port.in.GameRecordUseCase;
import com.system.insight.gamerecord.application.port.out.GameRecordPersistencePort;
import com.system.insight.gamerecord.domain.model.GameRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GameRecordService implements GameRecordUseCase {
    private final GameRecordPersistencePort gameRecordPersistencePort;

    @Override
    public GameRecord recordGame(String userId, Long score, LocalDateTime playAt) {
        GameRecord gameRecord = GameRecord.create(userId, score, playAt);
        return gameRecordPersistencePort.save(gameRecord);
    }

    @Override
    public List<GameRecord> findTop10ByOrderByScoreDesc() {
        return gameRecordPersistencePort.findTop10ByOrderByScoreDesc();
    }
} 