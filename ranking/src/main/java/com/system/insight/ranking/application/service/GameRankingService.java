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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GameRankingService implements GameRankingUseCase {
    
    private final RedisTemplate<String, String> redisTemplate;
    private final RankingPersistencePort rankingPersistencePort;
    private static final String RANKING_KEY = "game:ranking";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    @CacheEvict(cacheNames = "ranking", allEntries = true)
    public void registerScore(GameScoreRequest request) {
        LocalDateTime now = LocalDateTime.now();
        
        // Redis ZSET에 점수 등록
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(RANKING_KEY, request.userId(), request.score());
        
        // 현재 순위 조회 (0-based index를 1-based rank로 변환)
        Long zeroBasedRank = zSetOps.reverseRank(RANKING_KEY, request.userId());
        int rank = (zeroBasedRank != null) ? zeroBasedRank.intValue() + 1 : 1;
        
        // MySQL에 저장
        Ranking newRanking = Ranking.create(
            request.userId(),
            request.nickname(),
            request.profileImageUrl(),
            request.score(),
            rank,
            now
        );
        rankingPersistencePort.save(newRanking);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "ranking", key = "'top10'")
    public List<GameRankingResponse> getTop10Rankings() {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = 
            zSetOps.reverseRangeWithScores(RANKING_KEY, 0, 9);
            
        if (rangeWithScores == null || rangeWithScores.isEmpty()) {
            return rankingPersistencePort.findTopRankings(10, LocalDateTime.now()).stream()
                .map(ranking -> GameRankingResponse.from(
                    ranking.getRanking(),
                    ranking.getNickname(),
                    ranking.getProfileImageUrl(),
                    ranking.getScore(),
                    ranking.getSnapshotTime().format(DATE_FORMATTER)
                ))
                .collect(Collectors.toList());
        }
        
        return rangeWithScores.stream()
            .map(tuple -> {
                String userId = tuple.getValue();
                Ranking ranking = rankingPersistencePort.findByUserId(userId)
                    .orElseThrow(() -> new IllegalStateException("Ranking not found for user: " + userId));
                
                Long rankScore = zSetOps.reverseRank(RANKING_KEY, userId);
                
                return GameRankingResponse.from(
                    Math.toIntExact(rankScore + 1),
                    ranking.getNickname(),
                    ranking.getProfileImageUrl(),
                    Objects.requireNonNull(tuple.getScore()).longValue(),
                    ranking.getSnapshotTime().format(DATE_FORMATTER)
                );
            })
            .collect(Collectors.toList());
    }
} 