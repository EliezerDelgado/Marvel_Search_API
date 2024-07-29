package com.eliezer.marvel_characters.models.responses

data class DataContainer(
    val total: String,
    val results: List<Character>,
)