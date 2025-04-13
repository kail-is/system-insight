package com.system.insight.ranking.application.service;

import com.system.insight.ranking.adapter.in.web.dto.GameRankingResponse;
import com.system.insight.ranking.adapter.in.web.dto.GameScoreRequest;
import com.system.insight.ranking.application.port.in.GameRankingUseCase;
import com.system.insight.ranking.application.port.out.RankingPersistencePort;
import com.system.insight.ranking.domain.model.Ranking;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameRankingService implements GameRankingUseCase {
    
    private final RedisTemplate<String, String> redisTemplate;
    private final RankingPersistencePort rankingPersistencePort;
    private static final String RANKING_KEY = "game:ranking";
    
    @Override
    @CacheEvict(cacheNames = "ranking", allEntries = true)
    public void registerScore(GameScoreRequest request) {
        LocalDateTime now = LocalDateTime.now();
        
        // Redis ZSET에 점수 등록
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(RANKING_KEY, request.userId(), request.score());
        
        // 현재 순위 조회
        Long rank = zSetOps.reverseRank(RANKING_KEY, request.userId());
        
        // MySQL에 저장
        Ranking newRanking = Ranking.create(
            request.userId(),
            request.nickname(),
            request.profileImageUrl(),
            request.score(),
            rank != null ? rank.intValue() + 1 : 0,
            now
        );
        rankingPersistencePort.save(newRanking);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ranking", key = "'top10'")
    public List<GameRankingResponse> getTop10Rankings() {
        // Redis에서 상위 10명 조회
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = 
            zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9);
            
        if (rangeWithScores == null || rangeWithScores.isEmpty()) {
            // Redis에 데이터가 없으면 MySQL에서 조회
            return rankingPersistencePort.findTopRankings(10, LocalDateTime.now()).stream()
                .map(ranking -> new GameRankingResponse(
                    ranking.getRanking(),
                    ranking.getNickname(),
                    ranking.getProfileImageUrl(),
                    ranking.getScore(),
                    ranking.getSnapshotTime()
                ))
                .collect(Collectors.toList());
        }
        
        // Redis 데이터 반환
        return rangeWithScores.stream()
            .map(tuple -> {
                String userId = tuple.getValue();
                Ranking ranking = rankingPersistencePort.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Ranking not found for user: " + userId));
                    
                return new GameRankingResponse(
                    Math.toIntExact(zSetOps.reverseRank(RANKING_KEY, userId) + 1),
                    ranking.getNickname(),
                    ranking.getProfileImageUrl(),
                    tuple.getScore().longValue(),
                    ranking.getSnapshotTime()
                );
            })
            .collect(Collectors.toList());
    }
} 