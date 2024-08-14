package com.eliezer.marvel_characters.data.mappers

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.responses.character.CharacterDataContainer
import com.eliezer.marvel_characters.models.responses.comic.ComicDataContainer

fun CharacterDataContainer.mapToListCharacter() : List<Character> =
    results.map {
        Character(
            id = it.id?: 0,
            name = it.name ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension,
            description = it.description?: ""
        )
    }
fun ComicDataContainer.mapToListComic() : List<Comic> =
    results.map {
        Comic(
            title = it.title ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension
        )
    }
