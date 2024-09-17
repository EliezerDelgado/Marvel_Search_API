package com.eliezer.marvel_search_api.models.responses.comic

import com.google.gson.annotations.SerializedName


data class ComicDataContainer (

  @SerializedName("offset"  ) var offset  : Int?               = null,
  @SerializedName("limit"   ) var limit   : Int?               = null,
  @SerializedName("total"   ) var total   : Int?               = null,
  @SerializedName("count"   ) var count   : Int?               = null,
  @SerializedName("results" ) var results : ArrayList<ComicResponse> = arrayListOf()

)
