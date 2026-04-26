package com.threatlens.api.translator

data class FindingTranslateResponse(
    val audience: String,
    val title: String,
    val summary: String,
    val riskLevel: String,
    val impact: String,
    val recommendedAction: String,
    val explanation: String,
    val keyTerms: List<String>
)