package com.systeminsight.coupon.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(
                error = "INVALID_REQUEST",
                message = e.message ?: "잘못된 요청입니다"
            ))
    }
    
    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(e: IllegalStateException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse(
                error = "BUSINESS_ERROR", 
                message = e.message ?: "처리할 수 없는 요청입니다"
            ))
    }
    
    @ExceptionHandler(Exception::class)
    fun handleGeneral(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError()
            .body(ErrorResponse(
                error = "INTERNAL_ERROR",
                message = "서버 오류가 발생했습니다"
            ))
    }
}

data class ErrorResponse(
    val error: String,
    val message: String
)
