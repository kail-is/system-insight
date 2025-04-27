package com.system.insight.ranking.adapter.out.redis;

import com.system.insight.ranking.adapter.out.dto.UserInfoDto;
import com.system.insight.ranking.application.port.out.UserProfileRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

@Repository
public class RedisUserProfileRepository implements UserProfileRepository {
    private static final String USER_INFO_KEY_PREFIX = "user:info:";
    private final RedisTemplate<String, String> redisTemplate;

    public RedisUserProfileRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserInfoDto findById(String userId) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Map<String, String> userInfo = Objects.requireNonNull(
            hashOps.entries(USER_INFO_KEY_PREFIX + userId),
            "User info cannot be null"
        );
        
        return new UserInfoDto(
            userInfo.get("nickname"),
            userInfo.get("profileImageUrl"),
            userInfo.get("lastPlayedAt")
        );
    }

    @Override
    public void save(String userId, UserInfoDto userInfo) {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String key = USER_INFO_KEY_PREFIX + userId;
        hashOps.put(key, "nickname", userInfo.nickname());
        hashOps.put(key, "profileImageUrl", userInfo.profileImageUrl());
        hashOps.put(key, "lastPlayedAt", userInfo.lastPlayedAt());
    }
}