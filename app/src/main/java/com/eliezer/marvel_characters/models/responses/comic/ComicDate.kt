package com.eliezer.marvel_characters.models.responses.comic

import com.google.gson.annotations.SerializedName


data class ComicDate (

  @SerializedName("type" ) var type : String? = null,
  @SerializedName("date" ) var date : String? = null

)