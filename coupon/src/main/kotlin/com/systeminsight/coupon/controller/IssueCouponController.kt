package com.systeminsight.coupon.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/coupons")
class IssueCouponController {

    @PostMapping("/issue")
    fun issueCoupon(@RequestBody request: IssueCouponRequest): IssueCouponResponse {
        // TODO: 실제 구현은 추후 진행
        return IssueCouponResponse(
            couponId = UUID.randomUUID(),
            amount = request.amount,
            message = "쿠폰이 발급되었습니다."
        )
    }
}

data class IssueCouponRequest(
    val userId: String,
    val amount: Long
)

data class IssueCouponResponse(
    val couponId: UUID,
    val amount: Long,
    val message: String
)