package com.eliezer.marvel_search_api.models.responses.comic

import com.google.gson.annotations.SerializedName


data class ComicPrice (

  @SerializedName("type"  ) var type  : String? = null,
  @SerializedName("price" ) var price : Float?    = null

)