package com.eliezer.marvel_characters.models.responses.comic

import com.google.gson.annotations.SerializedName


data class ComicDataWrapper (

  @SerializedName("code"            ) var code            : Int?    = null,
  @SerializedName("status"          ) var status          : String? = null,
  @SerializedName("copyright"       ) var copyright       : String? = null,
  @SerializedName("attributionText" ) var attributionText : String? = null,
  @SerializedName("attributionHTML" ) var attributionHTML : String? = null,
  @SerializedName("data"            ) var data            : ComicDataContainer?   = ComicDataContainer(),
  @SerializedName("etag"            ) var etag            : String? = null

)
