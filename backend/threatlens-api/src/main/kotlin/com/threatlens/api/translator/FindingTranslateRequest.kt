package com.threatlens.api.translator

data class FindingTranslateRequest(
    val finding: String,
    val audience: String = "analyst"
)