package com.system.insight.ranking.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게임 랭킹 응답")
public record GameRankingResponse(
    @Schema(description = "순위", example = "1")
    int rank,
    
    @Schema(description = "닉네임", example = "게이머")
    String nickname,
    
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    String profileImageUrl,
    
    @Schema(description = "총 점수", example = "1000")
    long totalScore,
    
    @Schema(description = "마지막 플레이 시간", example = "2024-03-14 15:30:00")
    String lastPlayedAt
) {
    public static GameRankingResponse from(int rank, String nickname, String profileImageUrl, long score, String lastPlayedAt) {
        return new GameRankingResponse(rank, nickname, profileImageUrl, score, lastPlayedAt);
    }
} 