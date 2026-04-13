package com.threatlens.api.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Base64

@RestController
@RequestMapping("/api/jwt")
class JwtController {

    private val objectMapper = jacksonObjectMapper()

    @PostMapping("/analyze")
    fun analyze(@RequestBody request: JwtAnalyzeRequest): JwtAnalyzeResponse {
        val parts = request.token.split(".")

        if (parts.size != 3) {
            return JwtAnalyzeResponse(
                validFormat = false,
                header = null,
                payload = null,
                algorithm = null,
                findings = listOf("Token must have exactly 3 parts.")
            )
        }

        return try {
            val headerJson = decodeBase64Url(parts[0])
            val payloadJson = decodeBase64Url(parts[1])

            val header = objectMapper.readValue(headerJson, Map::class.java) as Map<String, Any?>
            val payload = objectMapper.readValue(payloadJson, Map::class.java) as Map<String, Any?>

            val findings = mutableListOf<String>()
            val algorithm = header["alg"]?.toString()

            if (algorithm != null) {
                findings.add("Token uses $algorithm.")
            }

            if (payload.containsKey("exp")) {
                findings.add("Token contains an expiration claim.")
            } else {
                findings.add("Token does not contain an expiration claim.")
            }

            JwtAnalyzeResponse(
                validFormat = true,
                header = header,
                payload = payload,
                algorithm = algorithm,
                findings = findings
            )
        } catch (e: Exception) {
            JwtAnalyzeResponse(
                validFormat = false,
                header = null,
                payload = null,
                algorithm = null,
                findings = listOf("Token could not be decoded.", e.message ?: "Unknown error")
            )
        }
    }

    private fun decodeBase64Url(value: String): String {
        val decoded = Base64.getUrlDecoder().decode(value)
        return String(decoded, Charsets.UTF_8)
    }
}