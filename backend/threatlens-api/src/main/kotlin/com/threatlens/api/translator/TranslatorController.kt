package com.threatlens.api.translator

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/translator")
class TranslatorController(
    private val translatorService: TranslatorService
) {

    @PostMapping("/analyze")
    fun analyze(@RequestBody request: FindingTranslateRequest): ResponseEntity<FindingTranslateResponse> {
        if (request.finding.isBlank()) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(
            translatorService.translate(
                finding = request.finding,
                audience = request.audience
            )
        )
    }
}