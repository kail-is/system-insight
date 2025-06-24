package com.systeminsight.coupon.domain

enum class CouponType(val discountAmount: Int, val count: Int) {
    DISCOUNT_1000(1000, 50),  // 1,000원 할인 - 50장
    DISCOUNT_2000(2000, 30),  // 2,000원 할인 - 30장  
    DISCOUNT_3000(3000, 20)   // 3,000원 할인 - 20장
}
