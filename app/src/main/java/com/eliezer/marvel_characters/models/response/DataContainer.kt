package com.eliezer.marvel_characters.models.response

data class DataContainer(
    val total: String,
    val results: List<Character>,
)