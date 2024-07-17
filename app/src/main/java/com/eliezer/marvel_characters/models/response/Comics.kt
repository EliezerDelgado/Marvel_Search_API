package com.eliezer.marvel_characters.models.response

data class Comics(
    val available: String,
    val returned: String,
    val collectionURI: String,
    val items: List<ComicSummary>,
)