package com.threatlens.api.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@RestController
@RequestMapping("/api/jwt")
class JwtTestController {

    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/test-token")
    fun getTestToken(): Map<String, String> {
        val secret = "mysecret123"

        val header = mapOf(
            "alg" to "HS256",
            "typ" to "JWT"
        )

        val payload = mapOf(
            "userId" to 123,
            "iss" to "ThreatLens",
            "aud" to "FrancescoDemo",
            "iat" to 1700000000,
            "exp" to 2000000000
        )

        val encodedHeader = base64UrlEncode(objectMapper.writeValueAsBytes(header))
        val encodedPayload = base64UrlEncode(objectMapper.writeValueAsBytes(payload))
        val signingInput = "$encodedHeader.$encodedPayload"
        val signature = hmacSha256(signingInput, secret)

        val token = "$signingInput.$signature"

        return mapOf(
            "token" to token,
            "secret" to secret
        )
    }

    private fun hmacSha256(data: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val key = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        mac.init(key)
        return base64UrlEncode(mac.doFinal(data.toByteArray(StandardCharsets.UTF_8)))
    }

    private fun base64UrlEncode(bytes: ByteArray): String {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}