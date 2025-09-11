package com.system.insight.gamerecord.application.port.in;

import com.system.insight.gamerecord.domain.model.GameRecord;

import java.time.LocalDateTime;
import java.util.List;
public interface GameRecordUseCase {
    GameRecord recordGame(String userId, Long score, LocalDateTime playAt);
    List<GameRecord> findTop10ByOrderByScoreDesc();
} 