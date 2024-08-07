package com.eliezer.marvel_characters.data.mappers

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.responses.character.DataContainerResponse
import com.eliezer.marvel_characters.models.responses.comic.ComicDataContainer

fun DataContainerResponse.mapToListCharacter() : List<Character> =
    results.map {
        Character(
            name = it.name ?: "",
            urlPicture = it.thumbnail.path + "." + it.thumbnail.extension
        )
    }
fun ComicDataContainer.mapToListComic() : List<Comic> =
    results.map {
        Comic(
            title = it.title ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension
        )
    }
