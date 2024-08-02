package com.eliezer.marvel_characters.models.responses
data class SeriesListResponse(
    val available: String,
    val collectionURI: String,
    val items: List<ComicSummaryResponse>,
    val returned: String
)