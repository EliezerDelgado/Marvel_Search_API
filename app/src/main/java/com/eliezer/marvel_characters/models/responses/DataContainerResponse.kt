package com.eliezer.marvel_characters.models.responses

data class DataContainerResponse(
    val total: String,
    val results: List<CharacterResponse>,
)