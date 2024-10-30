package com.eliezer.marvel_search_api.data.mappers

import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataContainer
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataContainer
import com.google.firebase.auth.AuthResult

fun CharacterDataContainer.mapToListCharacter() : Characters = Characters(
    total = total?:0,
    search = "",
    listCharacters = ArrayList(results.map {
        Character(
            id = it.id?: -1,
            name = it.name ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension,
            description = it.description?: ""
        )
    }))
fun ComicDataContainer.mapToListComic() :Comics = Comics(
    total = total?:0,
    search = "",
    listComics = ArrayList(results.map {
        Comic(
            id = it.id?: -1,
            title = it.title ?: "",
            urlPicture = it.thumbnail?.path + "." + it.thumbnail?.extension,
            description = it.description ?: ""
        )
    }))
fun AuthResult.mapToUserAccount() : UserAccount = 
    UserAccount(
        name = user?.displayName ?: "",
        email = user?.email?: "",
        photoUrl = user?.photoUrl
    )