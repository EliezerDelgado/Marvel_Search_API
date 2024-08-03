package com.eliezer.marvel_characters.models.responses.character

data class ComicsResponse(
    val available: String,
    val returned: String,
    val collectionURI: String,
    val items: List<ComicSummaryResponse>,
)