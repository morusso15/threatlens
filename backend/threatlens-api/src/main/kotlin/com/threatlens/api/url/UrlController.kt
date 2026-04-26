package com.threatlens.api.url

import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/url")
class UrlController {

    private val suspiciousWords = listOf(
        "login", "verify", "update", "secure", "security", "account",
        "bank", "password", "billing", "gift", "free", "alert",
        "support", "reset", "confirm", "wallet", "invoice", "payment"
    )

    private val knownBrands = listOf(
        "amazon", "paypal", "google", "microsoft", "apple", "netflix",
        "facebook", "instagram", "chase", "bankofamerica", "wellsfargo",
        "dropbox", "docusign", "github"
    )

    private val safeBrandDomains = mapOf(
        "amazon" to listOf("amazon.com"),
        "paypal" to listOf("paypal.com"),
        "google" to listOf("google.com"),
        "microsoft" to listOf("microsoft.com", "live.com", "office.com"),
        "apple" to listOf("apple.com"),
        "netflix" to listOf("netflix.com"),
        "facebook" to listOf("facebook.com"),
        "instagram" to listOf("instagram.com"),
        "github" to listOf("github.com")
    )

    private val riskyTlds = listOf(
        "xyz", "top", "click", "zip", "mov", "tk", "cn", "ru", "rest", "support"
    )

    @PostMapping("/analyze")
    fun analyze(@RequestBody request: UrlAnalyzeRequest): UrlAnalyzeResponse {
        val findings = mutableListOf<String>()
        var risk = 0

        val input = request.url.trim()
        val normalizedInput = if (!input.startsWith("http://") && !input.startsWith("https://")) {
            "http://$input"
        } else {
            input
        }

        val uri = try {
            URI(normalizedInput)
        } catch (e: Exception) {
            return invalidUrl(input)
        }

        val host = uri.host?.lowercase()

        if (host.isNullOrBlank()) {
            return invalidUrl(input)
        }

        val fullUrl = normalizedInput.lowercase()
        val usesHttps = uri.scheme == "https"
        val isIp = host.matches(Regex("""^\d{1,3}(\.\d{1,3}){3}$"""))

        if (!usesHttps) {
            findings.add("URL does not use HTTPS.")
            risk += 25
        }

        if (isIp) {
            findings.add("URL uses an IP address instead of a domain.")
            risk += 30
        }

        val matchedKeywords = suspiciousWords.filter { fullUrl.contains(it) }

        if (matchedKeywords.isNotEmpty()) {
            findings.add("URL contains suspicious keywords: ${matchedKeywords.joinToString(", ")}.")
            risk += (matchedKeywords.size * 10).coerceAtMost(35)
        }

        val matchedBrands = knownBrands.filter { brand ->
            host.contains(brand) && !isKnownSafeBrandDomain(host, brand)
        }

        if (matchedBrands.isNotEmpty()) {
            findings.add("URL may be impersonating a known brand: ${matchedBrands.joinToString(", ")}.")
            risk += 25
        }

        val tld = host.substringAfterLast('.', missingDelimiterValue = "")

        if (tld in riskyTlds) {
            findings.add("URL uses a commonly abused or suspicious top-level domain: .$tld.")
            risk += 20
        }

        val subdomainCount = host.split(".").size - 2
        if (subdomainCount >= 3) {
            findings.add("URL contains many subdomains, which can be suspicious.")
            risk += 15
        }

        val hyphenCount = host.count { it == '-' }
        if (hyphenCount >= 2) {
            findings.add("Hostname uses multiple hyphens, which can indicate deceptive structure.")
            risk += 10
        }

        if (fullUrl.contains("@")) {
            findings.add("URL contains an @ symbol, which can be used to hide the real destination.")
            risk += 25
        }

        if (host.length > 45) {
            findings.add("Hostname is unusually long.")
            risk += 10
        }

        if (findings.isEmpty()) {
            findings.add("No obvious phishing indicators were detected.")
        }

        return UrlAnalyzeResponse(
            originalUrl = input,
            normalizedUrl = uri.toString(),
            hostname = host,
            usesHttps = usesHttps,
            IpAddress = isIp,
            suspiciousKeywords = matchedKeywords,
            riskScore = risk.coerceIn(0, 100),
            findings = findings
        )
    }

    private fun isKnownSafeBrandDomain(host: String, brand: String): Boolean {
        val safeDomains = safeBrandDomains[brand] ?: listOf("$brand.com")
        return safeDomains.any { safeDomain ->
            host == safeDomain || host.endsWith(".$safeDomain")
        }
    }

    private fun invalidUrl(input: String): UrlAnalyzeResponse {
        return UrlAnalyzeResponse(
            originalUrl = input,
            normalizedUrl = null,
            hostname = null,
            usesHttps = false,
            IpAddress = false,
            suspiciousKeywords = emptyList(),
            riskScore = 100,
            findings = listOf("Invalid URL format.")
        )
    }
}