package com.threatlens.api.jwt

data class JwtAnalyzeResponse(
    val validFormat: Boolean,
    val header: Map<String, Any?>?,
    val payload: Map<String, Any?>?,
    val algorithm: String?,
    val findings: List<String>
)