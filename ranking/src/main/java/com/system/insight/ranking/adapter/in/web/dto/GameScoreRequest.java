package com.system.insight.ranking.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "게임 점수 등록 요청")
public record GameScoreRequest(
    @Schema(description = "닉네임", example = "DragonSlayer")
    @NotBlank(message = "닉네임은 필수입니다.")
    String nickname,
    
    @Schema(description = "사용자 ID", example = "khs")
    @NotBlank(message = "사용자 ID는 필수입니다.")
    String userId,
    
    @Schema(description = "프로필 이미지 URL", example = "https://cdn.example.com/images/dragon.png")
    @NotBlank(message = "프로필 이미지 URL은 필수입니다.")
    String profileImageUrl,
    
    @Schema(description = "게임 점수", example = "980")
    @NotNull(message = "점수는 필수입니다.")
    @PositiveOrZero(message = "점수는 0 이상이어야 합니다.")
    Long score
) {} 