package com.threatlens.api.url

import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/url")
class UrlController {

    private val suspiciousWords = listOf(
        "login", "verify", "update", "secure", "account", "bank", "password"
    )

    @PostMapping("/analyze")
    fun analyze(@RequestBody request: UrlAnalyzeRequest): UrlAnalyzeResponse {
        val findings = mutableListOf<String>()
        var risk = 0

        val input = request.url.trim()

        val uri = try {
            URI(if (!input.startsWith("http")) "http://$input" else input)
        } catch (e: Exception) {
            return UrlAnalyzeResponse(
                originalUrl = input,
                normalizedUrl = null,
                hostname = null,
                usesHttps = false,
                isIpAddress = false,
                suspiciousKeywords = emptyList(),
                riskScore = 100,
                findings = listOf("Invalid URL format.")
            )
        }

        val host = uri.host
        val usesHttps = uri.scheme == "https"

        if (!usesHttps) {
            findings.add("URL does not use HTTPS.")
            risk += 20
        }

        val isIp = host?.matches(Regex("""\d+\.\d+\.\d+\.\d+""")) == true
        if (isIp) {
            findings.add("URL uses an IP address instead of a domain.")
            risk += 30
        }

        val matchedKeywords = suspiciousWords.filter {
            input.lowercase().contains(it)
        }

        if (matchedKeywords.isNotEmpty()) {
            findings.add("URL contains suspicious keywords: ${matchedKeywords.joinToString()}.")
            risk += 20
        }

        if (host != null && host.count { it == '.' } > 3) {
            findings.add("URL contains many subdomains, which can be suspicious.")
            risk += 15
        }

        return UrlAnalyzeResponse(
            originalUrl = input,
            normalizedUrl = uri.toString(),
            hostname = host,
            usesHttps = usesHttps,
            isIpAddress = isIp,
            suspiciousKeywords = matchedKeywords,
            riskScore = risk.coerceAtMost(100),
            findings = findings
        )
    }
}