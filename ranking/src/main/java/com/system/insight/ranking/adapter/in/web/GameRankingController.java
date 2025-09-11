package com.system.insight.ranking.adapter.in.web;

import com.system.insight.ranking.adapter.in.web.dto.GameRankingResponse;
import com.system.insight.ranking.adapter.in.web.dto.GameScoreRequest;
import com.system.insight.ranking.application.port.in.GameRankingUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Game Ranking", description = "게임 랭킹 API")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GameRankingController {
    
    private final GameRankingUseCase gameRankingUseCase;
    
    @Operation(summary = "게임 점수 등록", description = "사용자의 게임 점수를 등록합니다.")
    @PostMapping("game/scores")
    public ResponseEntity<Void> registerScore(@RequestBody @Valid GameScoreRequest request) {
        gameRankingUseCase.registerScore(request);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "상위 10명 랭킹 조회", description = "현재 상위 10명의 랭킹 정보를 조회합니다.")
    @GetMapping("game-ranking/top10")
    public ResponseEntity<List<GameRankingResponse>> getTop10Rankings() {
        return ResponseEntity.ok(gameRankingUseCase.getTop10Rankings());
    }
} 