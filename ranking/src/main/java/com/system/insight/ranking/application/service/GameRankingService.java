package com.system.insight.ranking.application.service;

import com.system.insight.ranking.adapter.in.web.dto.GameRankingResponse;
import com.system.insight.ranking.adapter.in.web.dto.GameScoreRequest;
import com.system.insight.ranking.application.port.in.GameRankingUseCase;
import com.system.insight.gamerecord.application.port.in.GameRecordUseCase;
import com.system.insight.gamerecord.domain.model.GameRecord;
import com.system.insight.user.application.port.in.UserUseCase;
import com.system.insight.user.domain.model.User;
import com.system.insight.ranking.adapter.out.dto.UserInfoDto;
import com.system.insight.ranking.application.port.in.UserProfileUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class GameRankingService implements GameRankingUseCase {
    
    private final RedisTemplate<String, String> redisTemplate;

    private final GameRecordUseCase gameRecordUseCase;
    private final UserProfileUseCase userProfileUseCase;
    private final UserUseCase userUseCase;

    private static final String RANKING_KEY = "game:ranking";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    @Transactional(readOnly = true)
    public List<GameRankingResponse> getTop10Rankings() {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        // 1. Redis ZSET에서 랭킹 조회
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = 
            zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9);

        // 2. Redis 조회 결과가 없으면 GameRecord 조회
        if (rangeWithScores == null || rangeWithScores.isEmpty()) {
            List<GameRecord> records = gameRecordUseCase.findTop10ByOrderByScoreDesc();
            return IntStream.range(0, records.size())
                .mapToObj(index -> {
                    GameRecord record = records.get(index);
                    return Optional.ofNullable(userUseCase.findByUserId(record.getUserId()))
                        .map(user -> GameRankingResponse.from(
                            index + 1,
                            user.getNickname(),
                            user.getProfileImageUrl(),
                            record.getScore(),
                            record.getPlayAt().format(DATE_FORMATTER)
                        )).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        
        // 3. Redis 결과 처리
        return rangeWithScores.stream()
            .map(tuple -> {
                String userId = Objects.requireNonNull(tuple.getValue(), "User ID cannot be null");
                UserInfoDto userInfo = userProfileUseCase.getUserProfile(userId);
                Long rankScore = zSetOps.reverseRank(RANKING_KEY, userId);
                
                return GameRankingResponse.from(
                    Math.toIntExact(Objects.requireNonNull(rankScore, "Rank score cannot be null") + 1),
                    userInfo.nickname(),
                    userInfo.profileImageUrl(),
                    Objects.requireNonNull(tuple.getScore()).longValue(),
                    userInfo.lastPlayedAt()
                );
            })
            .collect(Collectors.toList());
    }
    
    @Override
    @CacheEvict(cacheNames = "ranking", allEntries = true)
    public void registerScore(GameScoreRequest request) {
        LocalDateTime now = LocalDateTime.now();
        
        // 1. Redis ZSET에 점수 등록
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(RANKING_KEY, request.userId(), request.score());
        
        // 2. Redis Hash에 사용자 정보 저장
        UserInfoDto userInfo = new UserInfoDto(
            request.nickname(),
            request.profileImageUrl(),
            now.format(DATE_FORMATTER)
        );
        userProfileUseCase.saveUserProfile(request.userId(), userInfo);
        
        // 3. GameRecord 저장 
        gameRecordUseCase.recordGame(request.userId(), request.score(), now);
    }
}
