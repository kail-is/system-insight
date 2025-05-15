package com.system.insight.gamerecord.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GameRecordRepository extends JpaRepository<GameRecordJpaEntity, Long> {
    List<GameRecordJpaEntity> findTop10ByOrderByScoreDesc();
} 