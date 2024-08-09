package com.eliezer.marvel_characters.models.responses

import com.google.gson.annotations.SerializedName


data class SeriesSummary (

  @SerializedName("resourceURI" ) var resourceURI : String? = null,
  @SerializedName("name"        ) var name        : String? = null

)