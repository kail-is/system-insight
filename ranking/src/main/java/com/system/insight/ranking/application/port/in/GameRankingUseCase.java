package com.system.insight.ranking.application.port.in;

import com.system.insight.ranking.adapter.in.web.dto.GameRankingResponse;
import com.system.insight.ranking.adapter.in.web.dto.GameScoreRequest;

import java.util.List;

public interface GameRankingUseCase {
    void registerScore(GameScoreRequest request);
    List<GameRankingResponse> getTop10Rankings();
} 