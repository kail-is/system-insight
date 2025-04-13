package com.system.insight.gamerecord.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRecordRepository extends JpaRepository<GameRecordJpaEntity, Long> {
} 