package com.systeminsight.coupon.service

import com.systeminsight.coupon.domain.CouponType
import com.systeminsight.coupon.event.CouponIssuedEvent
import com.systeminsight.coupon.repository.UserRepository
import org.redisson.api.RedissonClient
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CouponService(
    private val redissonClient: RedissonClient,
    private val userRepository: UserRepository,
    private val kafkaTemplate: KafkaTemplate<String, CouponIssuedEvent>
) {

    fun initializeCouponPool() {
        val coupons = mutableListOf<String>()
        CouponType.entries.forEach { type ->
            repeat(type.count) { index ->
                coupons.add("${type.name}:${index}")
            }
        }
        
        val set = redissonClient.getSet<String>(COUPON_POOL_KEY)
        set.clear()
        set.addAll(coupons.shuffled())
        
        println("쿠폰 풀 초기화 완료: ${coupons.size}개")
    }
    
    fun issueCoupon(userId: String): String {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("존재하지 않는 사용자입니다.")
        }

        val userIssuedKey = USER_ISSUED_KEY_PREFIX + userId
        if (redissonClient.getBucket<String>(userIssuedKey).isExists) {
            throw IllegalStateException("이미 쿠폰을 발급받으셨습니다.")
        }
        
        val lock = redissonClient.getFairLock("lock:coupon:issue:$userId")
        
        return try {
            if (lock.tryLock(3, 5, TimeUnit.SECONDS)) {
                try {
                    val keys = listOf(COUPON_POOL_KEY, USER_ISSUED_KEY_PREFIX + userId)
                    val couponTypeName = redissonClient.getScript().eval<String>(
                        org.redisson.api.RScript.Mode.READ_WRITE,
                        LUA_SCRIPT,
                        org.redisson.api.RScript.ReturnType.VALUE,
                        keys
                    ) ?: throw IllegalStateException("쿠폰이 모두 소진되었습니다.")

                    val couponType = CouponType.valueOf(couponTypeName.split(":")[0])

                    // 카프카로 이벤트 발행
                    val event = CouponIssuedEvent(
                        userId = userId,
                        couponType = couponType.name,
                        discountAmount = couponType.discountAmount
                    )
                    kafkaTemplate.send(COUPON_ISSUED_TOPIC, userId, event)
                    
                    "쿠폰 발급 완료: ${couponType.discountAmount}원 할인"
                } finally {
                    if (lock.isHeldByCurrentThread) {
                        lock.unlock()
                    }
                }
            } else {
                throw IllegalStateException("처리 중입니다. 잠시 후 다시 시도해주세요.")
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw IllegalStateException("쿠폰 발급 중 오류가 발생했습니다.")
        }
    }


    companion object {
        private const val COUPON_POOL_KEY = "coupon:pool"
        private const val USER_ISSUED_KEY_PREFIX = "user:coupon:issued:"
        private const val COUPON_ISSUED_TOPIC = "coupon.issued"

        private val LUA_SCRIPT = """
            local coupon_pool_key = KEYS[1]
            local user_issued_key = KEYS[2]
            
            -- 문자열로 체크
            local existing = redis.call('GET', user_issued_key)
            if existing then
                return nil
            end
            
            local coupon_type = redis.call('SPOP', coupon_pool_key)
            if not coupon_type then
                return nil
            end
            
            redis.call('SETEX', user_issued_key, 86400, coupon_type)
            return coupon_type
        """.trimIndent()
    }
}
