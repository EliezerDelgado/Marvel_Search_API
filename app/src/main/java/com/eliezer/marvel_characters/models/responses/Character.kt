package com.eliezer.marvel_characters.models.responses

data class Character(
    val id : Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val series: SeriesList,
)