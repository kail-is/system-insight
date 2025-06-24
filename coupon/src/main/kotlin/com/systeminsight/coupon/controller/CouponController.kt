package com.systeminsight.coupon.controller

import com.systeminsight.coupon.service.CouponService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/coupons")
class IssueCouponController(
    private val couponService: CouponService
) {

    @PostMapping("/initialize")
    fun initialize(): ResponseEntity<String> {
        couponService.initializeCouponPool()
        return ResponseEntity.ok("쿠폰 풀 초기화 완료")
    }

    @PostMapping("/issue")
    fun issueCoupon(@RequestBody request: IssueCouponRequest): ResponseEntity<IssueCouponResponse> {
        return try {
            val result = couponService.issueCoupon(request.userId)
            ResponseEntity.ok(
                IssueCouponResponse(
                    couponId = UUID.randomUUID(),
                    amount = extractAmountFromResult(result),
                    message = result
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                IssueCouponResponse(
                    couponId = UUID.randomUUID(),
                    amount = 0,
                    message = e.message ?: "쿠폰 발급 실패"
                )
            )
        }
    }
    
    private fun extractAmountFromResult(result: String): Long {
        val regex = "(\\d+)원".toRegex()
        val matchResult = regex.find(result)
        return matchResult?.groupValues?.get(1)?.toLong() ?: 0L
    }
}

data class IssueCouponRequest(
    val userId: String,
    val amount: Long? = null
)

data class IssueCouponResponse(
    val couponId: UUID,
    val amount: Long,
    val message: String
)
