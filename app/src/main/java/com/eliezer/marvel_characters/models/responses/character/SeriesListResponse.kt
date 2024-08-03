package com.eliezer.marvel_characters.models.responses.character
data class SeriesListResponse(
    val available: String,
    val collectionURI: String,
    val items: List<ComicSummaryResponse>,
    val returned: String
)