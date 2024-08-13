package com.eliezer.marvel_characters.models.dataclass

import com.eliezer.marvel_characters.models.responses.character.ComicSummary
import kotlinx.serialization.Serializable

@Serializable
data class Character(val id : Int,val name: String, val urlPicture: String,val description : String,val comics : List<ComicSummary> = arrayListOf())