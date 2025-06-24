package com.systeminsight.coupon.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_issues")
data class CouponIssue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    val userId: String,
    val couponType: String,
    val discountAmount: Int,
    val issuedAt: LocalDateTime = LocalDateTime.now()
)
