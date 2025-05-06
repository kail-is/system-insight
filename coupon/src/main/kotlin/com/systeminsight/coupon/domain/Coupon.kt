package com.systeminsight.coupon.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.UUID

@Entity
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val couponId: UUID = UUID.randomUUID(),
    
    val userId: String,
    
    val amount: Long,
    
    val issuedAt: LocalDateTime = LocalDateTime.now(),
    
    val usedAt: LocalDateTime? = null,
    
    val isUsed: Boolean = false
) 