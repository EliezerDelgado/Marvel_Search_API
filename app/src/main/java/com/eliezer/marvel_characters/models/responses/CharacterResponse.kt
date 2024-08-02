package com.eliezer.marvel_characters.models.responses

data class CharacterResponse(
    val id : Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailResponse,
    val comics: ComicsResponse,
    val series: SeriesListResponse,
)