package com.systeminsight.coupon.repository

import com.systeminsight.coupon.domain.CouponIssue
import org.springframework.data.jpa.repository.JpaRepository

interface CouponIssueRepository : JpaRepository<CouponIssue, Long> {
    fun existsByUserId(userId: String): Boolean
}
