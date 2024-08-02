package com.eliezer.marvel_characters.data.mappers

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.responses.DataContainerResponse

fun DataContainerResponse.mapToListCharacter() : List<Character> =
    results.map {
        Character(
            name = it.name ?: "",
            urlPicture = it.thumbnail.path + "." + it.thumbnail.extension
        )
    }
