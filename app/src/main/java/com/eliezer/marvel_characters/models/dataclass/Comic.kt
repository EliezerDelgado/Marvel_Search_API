package com.eliezer.marvel_characters.models.dataclass

import com.eliezer.marvel_characters.models.responses.comic.Image
import com.google.gson.annotations.SerializedName

data class Comic(
val title              : String ,
val urlPicture: String
    )
