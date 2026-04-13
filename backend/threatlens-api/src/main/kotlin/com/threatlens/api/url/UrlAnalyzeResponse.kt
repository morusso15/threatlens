package com.threatlens.api.url

data class UrlAnalyzeResponse(
    val originalUrl: String,
    val normalizedUrl: String?,
    val hostname: String?,
    val usesHttps: Boolean,
    val IpAddress: Boolean,
    val suspiciousKeywords: List<String>,
    val riskScore: Int,
    val findings: List<String>
)