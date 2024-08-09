package com.eliezer.marvel_characters.models.responses

import com.google.gson.annotations.SerializedName


data class EventList (

  @SerializedName("available"     ) var available     : Int?              = null,
  @SerializedName("returned"      ) var returned      : Int?              = null,
  @SerializedName("collectionURI" ) var collectionURI : String?           = null,
  @SerializedName("items"         ) var items         : ArrayList<EventSummary> = arrayListOf()

)