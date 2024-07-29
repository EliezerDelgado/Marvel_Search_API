package com.eliezer.marvel_characters.models.responses

data class CharacterWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: DataContainer,
)