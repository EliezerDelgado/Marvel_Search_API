package com.eliezer.marvel_characters.models.responses
data class SeriesList(
    val available: String,
    val collectionURI: String,
    val items: List<ComicSummary>,
    val returned: String
)