package com.eliezer.marvel_characters.models.dataclass

import com.eliezer.marvel_characters.models.responses.character.ComicSummary

data class Character(val name: String, val urlPicture: String,val description : String,val comics : ArrayList<ComicSummary> = arrayListOf(),)