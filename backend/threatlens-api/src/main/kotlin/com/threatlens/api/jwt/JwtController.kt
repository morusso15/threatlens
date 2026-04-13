package com.threatlens.api.jwt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

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
                signature = null,
                expired = null,
                expiresAt = null,
                issuedAt = null,
                issuer = null,
                audience = null,
                signatureVerified = null,
                findings = listOf("Token must have exactly 3 parts.")
            )
        }

        return try {
            val headerJson = decodeBase64Url(parts[0])
            val payloadJson = decodeBase64Url(parts[1])

            @Suppress("UNCHECKED_CAST")
            val header = objectMapper.readValue(headerJson, Map::class.java) as Map<String, Any?>

            @Suppress("UNCHECKED_CAST")
            val payload = objectMapper.readValue(payloadJson, Map::class.java) as Map<String, Any?>

            val algorithm = header["alg"]?.toString()
            val signature = parts[2]

            val exp = payload["exp"]?.toEpochSeconds()
            val iat = payload["iat"]?.toEpochSeconds()
            val issuer = payload["iss"]?.toString()
            val audience = payload["aud"]

            val now = Instant.now().epochSecond
            val expired = exp?.let { it < now }

            val findings = mutableListOf<String>()

            if (algorithm != null) {
                findings.add("Token uses $algorithm.")
            } else {
                findings.add("Token does not specify an algorithm.")
            }

            if (algorithm.equals("none", ignoreCase = true)) {
                findings.add("Token uses 'none' algorithm, which is insecure.")
            }

            if (exp != null) {
                if (expired == true) {
                    findings.add("Token is expired.")
                } else {
                    findings.add("Token contains an expiration claim and is not expired.")
                }
            } else {
                findings.add("Token does not contain an expiration claim.")
            }

            if (iat != null) {
                findings.add("Token contains an issued-at claim.")
            }

            if (issuer != null) {
                findings.add("Token contains an issuer claim.")
            } else {
                findings.add("Token does not contain an issuer claim.")
            }

            if (audience != null) {
                findings.add("Token contains an audience claim.")
            } else {
                findings.add("Token does not contain an audience claim.")
            }

            val signatureVerified = verifySignatureIfPossible(
                algorithm = algorithm,
                signingInput = "${parts[0]}.${parts[1]}",
                providedSignature = parts[2],
                secret = request.secret?.takeIf { it.isNotBlank() }
            )

            when {
                request.secret.isNullOrBlank() ->
                    findings.add("No secret provided, so signature was not verified.")

                signatureVerified == true ->
                    findings.add("Signature verification succeeded using the provided secret.")

                signatureVerified == false ->
                    findings.add("Signature verification failed using the provided secret.")
            }

            JwtAnalyzeResponse(
                validFormat = true,
                header = header,
                payload = payload,
                algorithm = algorithm,
                signature = signature,
                expired = expired,
                expiresAt = exp,
                issuedAt = iat,
                issuer = issuer,
                audience = audience,
                signatureVerified = signatureVerified,
                findings = findings
            )
        } catch (e: Exception) {
            JwtAnalyzeResponse(
                validFormat = false,
                header = null,
                payload = null,
                algorithm = null,
                signature = null,
                expired = null,
                expiresAt = null,
                issuedAt = null,
                issuer = null,
                audience = null,
                signatureVerified = null,
                findings = listOf(
                    "Token could not be decoded.",
                    e.message ?: "Unknown error"
                )
            )
        }
    }

    private fun decodeBase64Url(value: String): String {
        val decoded = Base64.getUrlDecoder().decode(value)
        return String(decoded, Charsets.UTF_8)
    }

    private fun verifySignatureIfPossible(
        algorithm: String?,
        signingInput: String,
        providedSignature: String,
        secret: String?
    ): Boolean? {
        if (secret == null) return null

        return when (algorithm) {
            "HS256" -> verifyHmac("HmacSHA256", signingInput, providedSignature, secret)
            "HS384" -> verifyHmac("HmacSHA384", signingInput, providedSignature, secret)
            "HS512" -> verifyHmac("HmacSHA512", signingInput, providedSignature, secret)
            else -> null
        }
    }

    private fun verifyHmac(
        macAlgorithm: String,
        signingInput: String,
        providedSignature: String,
        secret: String
    ): Boolean {
        val mac = Mac.getInstance(macAlgorithm)
        val secretKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), macAlgorithm)
        mac.init(secretKey)

        val computed = mac.doFinal(signingInput.toByteArray(StandardCharsets.UTF_8))
        val encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(computed)

        return encoded == providedSignature
    }

    private fun Any.toEpochSeconds(): Long? {
        return when (this) {
            is Int -> this.toLong()
            is Long -> this
            is Double -> this.toLong()
            is Float -> this.toLong()
            is String -> this.toLongOrNull()
            else -> null
        }
    }
}