package com.eliezer.marvel_characters.data.mappers

import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Characters
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import com.eliezer.marvel_characters.models.responses.character.CharacterDataContainer
import com.eliezer.marvel_characters.models.responses.character.CharacterDataWrapper
import com.eliezer.marvel_characters.models.responses.comic.ComicDataContainer
import com.eliezer.marvel_characters.models.responses.comic.ComicDataWrapper

fun CharacterDataContainer.mapToListCharacter() : Characters = Characters(total?:0,
    ArrayList(results.map {
        Character(
            id = it.id?: -1,
            name = it.name ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension,
            description = it.description?: ""
        )
    }))
fun ComicDataContainer.mapToListComic() :Comics = Comics(total?:0,
    ArrayList(results.map {
        Comic(
            id = it.id?: -1,
            title = it.title ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension,
            description = it.description ?: ""
        )
    }))