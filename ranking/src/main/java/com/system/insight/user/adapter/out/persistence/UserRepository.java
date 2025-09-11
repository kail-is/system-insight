package com.system.insight.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByUserId(String userId);
    boolean existsByUserId(String userId);
} 