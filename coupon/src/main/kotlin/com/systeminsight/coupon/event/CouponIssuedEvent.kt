package com.systeminsight.coupon.event

data class CouponIssuedEvent(
    val userId: String,
    val couponType: String,
    val discountAmount: Int,
    val issuedAt: String = java.time.LocalDateTime.now().toString()
)
