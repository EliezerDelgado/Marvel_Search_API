package com.eliezer.marvel_characters.models.response
data class SeriesList(
    val available: String,
    val collectionURI: String,
    val items: List<ComicSummary>,
    val returned: String
)