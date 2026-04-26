package com.threatlens.api.translator

import org.springframework.stereotype.Service

@Service
class TranslatorService {

    fun translate(finding: String, audience: String): FindingTranslateResponse {
        val normalizedAudience = audience.lowercase().trim()
        val lower = finding.lowercase()

        val riskLevel = detectRiskLevel(lower)
        val keyTerms = detectKeyTerms(lower)
        val findingType = detectFindingType(lower)

        return when (normalizedAudience) {
            "executive" -> executiveResponse(finding, findingType, riskLevel, keyTerms)
            "developer" -> developerResponse(finding, findingType, riskLevel, keyTerms)
            "analyst" -> analystResponse(finding, findingType, riskLevel, keyTerms)
            else -> analystResponse(finding, findingType, riskLevel, keyTerms)
        }
    }

    private fun detectRiskLevel(finding: String): String {
        return when {
            listOf(
                "critical",
                "rce",
                "remote code execution",
                "unauthenticated",
                "privilege escalation",
                "authentication bypass"
            ).any { finding.contains(it) } -> "High"

            listOf(
                "sql injection",
                "xss",
                "cross-site scripting",
                "csrf",
                "sensitive data",
                "insecure direct object reference",
                "idor"
            ).any { finding.contains(it) } -> "Medium"

            listOf(
                "information disclosure",
                "misconfiguration",
                "deprecated",
                "missing header",
                "verbose error"
            ).any { finding.contains(it) } -> "Low"

            else -> "Unknown"
        }
    }

    private fun detectFindingType(finding: String): String {
        return when {
            finding.contains("sql injection") -> "SQL Injection"
            finding.contains("xss") || finding.contains("cross-site scripting") -> "Cross-Site Scripting"
            finding.contains("csrf") -> "Cross-Site Request Forgery"
            finding.contains("remote code execution") || finding.contains("rce") -> "Remote Code Execution"
            finding.contains("authentication bypass") -> "Authentication Bypass"
            finding.contains("privilege escalation") -> "Privilege Escalation"
            finding.contains("information disclosure") -> "Information Disclosure"
            finding.contains("missing header") -> "Missing Security Header"
            finding.contains("misconfiguration") -> "Security Misconfiguration"
            else -> "Security Finding"
        }
    }

    private fun detectKeyTerms(finding: String): List<String> {
        return listOf(
            "rce",
            "remote code execution",
            "sql injection",
            "xss",
            "cross-site scripting",
            "csrf",
            "authentication bypass",
            "privilege escalation",
            "information disclosure",
            "misconfiguration",
            "missing header",
            "sensitive data",
            "idor"
        ).filter { finding.contains(it) }
    }

    private fun executiveResponse(
        originalFinding: String,
        findingType: String,
        riskLevel: String,
        keyTerms: List<String>
    ): FindingTranslateResponse {
        return FindingTranslateResponse(
            audience = "executive",
            title = "$riskLevel Risk: $findingType",
            summary = "This issue could create business risk if exploited. It should be reviewed and prioritized based on exposure, affected users, and the sensitivity of impacted data.",
            riskLevel = riskLevel,
            impact = when (riskLevel) {
                "High" -> "Potential for major business impact, including unauthorized access, service disruption, data exposure, or reputational damage."
                "Medium" -> "Potential for meaningful security impact, especially if combined with other weaknesses or exposed to untrusted users."
                "Low" -> "Likely a security hygiene issue, but still worth addressing to reduce overall risk."
                else -> "More context is needed to determine business impact."
            },
            recommendedAction = when (riskLevel) {
                "High" -> "Prioritize remediation as soon as possible and confirm whether the affected system is externally exposed."
                "Medium" -> "Schedule remediation in the normal security backlog and validate whether exploitation is practical."
                "Low" -> "Address as part of routine hardening."
                else -> "Have a technical reviewer validate the finding and assign priority."
            },
            explanation = "Original finding: $originalFinding",
            keyTerms = keyTerms
        )
    }

    private fun developerResponse(
        originalFinding: String,
        findingType: String,
        riskLevel: String,
        keyTerms: List<String>
    ): FindingTranslateResponse {
        val lower = originalFinding.lowercase()

        val remediation = when {
            lower.contains("sql injection") ->
                "Use parameterized queries or prepared statements. Do not build SQL with string concatenation. Validate and constrain user input."

            lower.contains("xss") || lower.contains("cross-site scripting") ->
                "Escape user-controlled output, sanitize rich text input, avoid unsafe DOM APIs, and consider a strict Content Security Policy."

            lower.contains("csrf") ->
                "Require CSRF tokens for state-changing requests, use SameSite cookies, and validate request origin where appropriate."

            lower.contains("remote code execution") || lower.contains("rce") ->
                "Patch the vulnerable component, remove unsafe execution paths, validate input, and restrict access to affected functionality."

            lower.contains("authentication bypass") ->
                "Review authentication middleware, enforce authorization checks server-side, and add regression tests for protected routes."

            lower.contains("privilege escalation") ->
                "Check role and permission boundaries. Enforce authorization checks at the API/service layer, not only in the UI."

            else ->
                "Review the affected code path, validate all untrusted input, apply vendor patches, and add tests to prevent regression."
        }

        return FindingTranslateResponse(
            audience = "developer",
            title = "$findingType remediation guidance",
            summary = "This finding points to a technical weakness that should be fixed in the affected code path or configuration.",
            riskLevel = riskLevel,
            impact = "An attacker may be able to abuse this weakness depending on exposure, input control, authentication state, and affected functionality.",
            recommendedAction = remediation,
            explanation = "Focus on reproducing the issue, identifying the vulnerable code path, implementing the fix, and adding regression coverage.",
            keyTerms = keyTerms
        )
    }

    private fun analystResponse(
        originalFinding: String,
        findingType: String,
        riskLevel: String,
        keyTerms: List<String>
    ): FindingTranslateResponse {
        return FindingTranslateResponse(
            audience = "analyst",
            title = "$findingType triage",
            summary = "This finding should be validated, categorized, and prioritized based on exploitability and asset exposure.",
            riskLevel = riskLevel,
            impact = when (riskLevel) {
                "High" -> "High-risk findings may enable compromise of confidentiality, integrity, or availability."
                "Medium" -> "Medium-risk findings may be exploitable in realistic conditions but often require context or chaining."
                "Low" -> "Low-risk findings usually support defense-in-depth or hardening."
                else -> "The finding needs additional evidence before risk can be confidently assigned."
            },
            recommendedAction = "Confirm affected asset, verify reproduction steps, determine exposure, document evidence, and assign remediation priority.",
            explanation = "Original finding: $originalFinding",
            keyTerms = keyTerms
        )
    }
}