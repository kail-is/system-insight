package com.system.insight.gamerecord.application.port.in;

import com.system.insight.gamerecord.domain.model.GameRecord;

public interface GameRecordUseCase {
    GameRecord recordGame(String userId, String gameType, Long score, Integer playTime);
} 