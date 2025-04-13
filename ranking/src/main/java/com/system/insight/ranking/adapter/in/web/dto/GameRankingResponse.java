package com.system.insight.ranking.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "게임 랭킹 응답")
public record GameRankingResponse(
    @Schema(description = "순위", example = "1")
    Integer rank,
    
    @Schema(description = "사용자 ID", example = "khs")
    String nickname,

    @Schema(description = "사용자 이미지", example = "khs")
    String profileImageUrl,

    @Schema(description = "총 점수", example = "980")
    Long totalScore,
    
    @Schema(description = "마지막 플레이 시간", example = "2025-03-27T12:45:00Z")
    LocalDateTime lastPlayedAt
) {} 