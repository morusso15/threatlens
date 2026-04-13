package com.threatlens.api.jwt

data class JwtAnalyzeResponse(
    val validFormat: Boolean,
    val header: Map<String, Any?>?,
    val payload: Map<String, Any?>?,
    val algorithm: String?,
    val signature: String?,
    val expired: Boolean?,
    val expiresAt: Long?,
    val issuedAt: Long?,
    val issuer: String?,
    val audience: Any?,
    val signatureVerified: Boolean?,
    val findings: List<String>
)