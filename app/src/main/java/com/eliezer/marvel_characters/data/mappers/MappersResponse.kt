package com.eliezer.marvel_characters.data.mappers

import com.eliezer.marvel_characters.domain.models.Character
import com.eliezer.marvel_characters.models.response.DataContainer

fun DataContainer.mapToListCharacter() =
    results.map {
        Character(
            name = it.name ?: "",
            urlPicture = it.thumbnail.path + "." + it.thumbnail.extension
        )
    }.orEmpty()
