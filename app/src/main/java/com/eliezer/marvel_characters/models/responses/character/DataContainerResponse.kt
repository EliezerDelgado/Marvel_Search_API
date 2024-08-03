package com.eliezer.marvel_characters.models.responses.character

data class DataContainerResponse(
    val total: String,
    val results: List<CharacterResponse>
)