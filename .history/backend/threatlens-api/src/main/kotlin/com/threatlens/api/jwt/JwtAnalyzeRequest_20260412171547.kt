package com.threatlens.api.jwt

data class JwtAnalyzeRequest(
    val token: String,
    val secret: String? = null
)