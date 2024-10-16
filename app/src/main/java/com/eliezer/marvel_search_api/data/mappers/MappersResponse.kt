package com.eliezer.marvel_search_api.data.mappers

import com.eliezer.marvel_search_api.models.dataclass.Character
import com.eliezer.marvel_search_api.models.dataclass.Characters
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.models.dataclass.UserAccount
import com.eliezer.marvel_search_api.models.responses.character.CharacterDataContainer
import com.eliezer.marvel_search_api.models.responses.comic.ComicDataContainer
import com.google.firebase.auth.AuthResult
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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
fun AuthResult.mapToUserAccount() : UserAccount = UserAccount(user?.displayName ?: "",user?.email?: "",user?.photoUrl)