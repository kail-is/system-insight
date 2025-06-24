package com.systeminsight.coupon.repository

import com.systeminsight.coupon.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>
