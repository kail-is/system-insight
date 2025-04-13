package com.system.insight.gamerecord.application.port.out;

import com.system.insight.gamerecord.domain.model.GameRecord;

public interface GameRecordPersistencePort {
    GameRecord save(GameRecord gameRecord);
} 