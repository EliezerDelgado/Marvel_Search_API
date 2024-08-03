package com.eliezer.marvel_characters.models.responses.comic

import com.google.gson.annotations.SerializedName


data class Image (

  @SerializedName("path"      ) var path      : String? = null,
  @SerializedName("extension" ) var extension : String? = null

)