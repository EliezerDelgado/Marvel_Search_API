package com.eliezer.marvel_characters.models.responses.comic

import com.google.gson.annotations.SerializedName

data class TextObject(

    @SerializedName("type" ) var type : String? = null,
    @SerializedName("language"        ) var language        : String? = null,
    @SerializedName("text"        ) var text        : String? = null
)
