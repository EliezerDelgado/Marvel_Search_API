package com.eliezer.marvel_characters.models.response

data class CharacterWrapper(
    val code: Int,
    val status: String,
    val copyright: String,
    val data: DataContainer,
)