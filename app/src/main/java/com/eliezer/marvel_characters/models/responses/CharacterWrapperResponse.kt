package com.eliezer.marvel_characters.models.responses

data class CharacterWrapperResponse(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: DataContainerResponse,
)