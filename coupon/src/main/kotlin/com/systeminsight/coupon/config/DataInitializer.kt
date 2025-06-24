package com.systeminsight.coupon.config

import com.systeminsight.coupon.domain.User
import com.systeminsight.coupon.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val userRepository: UserRepository
) : CommandLineRunner {
    
    override fun run(vararg args: String?) {
        println("DataInitializer 실행 시작")
        
        val existingCount = userRepository.count()
        println("기존 사용자 수: $existingCount")
        
        if (existingCount == 0L) {
            val users = mutableListOf<User>()
            
            // 기본 사용자 5명
            users.addAll(listOf(
                User("user1", "김철수"),
                User("user2", "이영희"),
                User("user3", "박민수"),
                User("user4", "최지영"),
                User("user5", "정성호")
            ))
            
            // 테스트용 사용자 100명 추가
            repeat(100) { index ->
                users.add(User("test_user_$index", "테스트유저$index"))
            }
            
            println("사용자 ${users.size}명 생성 중...")
            userRepository.saveAll(users)
            println("사용자 ${users.size}명 초기화 완료")
        } else {
            println("이미 사용자가 존재합니다. 초기화 건너뜀")
        }
    }
}
