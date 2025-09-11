package com.system.insight.gamerecord.application.port.out;

import com.system.insight.gamerecord.domain.model.GameRecord;

import java.util.List;

public interface GameRecordPersistencePort {
    GameRecord save(GameRecord gameRecord);
    List<GameRecord> findTop10ByOrderByScoreDesc();
} 