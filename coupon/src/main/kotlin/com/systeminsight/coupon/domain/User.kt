package com.systeminsight.coupon.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id val userId: String,
    val name: String
)
