package com.systeminsight.coupon.service

import com.systeminsight.coupon.domain.CouponIssue
import com.systeminsight.coupon.event.CouponIssuedEvent
import com.systeminsight.coupon.repository.CouponIssueRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponEventConsumer(
    private val couponIssueRepository: CouponIssueRepository
) {
    
    @KafkaListener(topics = ["coupon.issued"])
    @Transactional
    fun handleCouponIssued(event: CouponIssuedEvent) {
        try {
            val couponIssue = CouponIssue(
                userId = event.userId,
                couponType = event.couponType,
                discountAmount = event.discountAmount,
                issuedAt = java.time.LocalDateTime.parse(event.issuedAt)
            )
            couponIssueRepository.save(couponIssue)
        } catch (e: Exception) {
            throw e
        }
    }
}
