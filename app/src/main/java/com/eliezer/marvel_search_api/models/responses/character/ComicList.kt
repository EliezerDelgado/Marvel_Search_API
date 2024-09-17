package com.eliezer.marvel_search_api.models.responses.character

import com.google.gson.annotations.SerializedName

data class ComicList(
    @SerializedName("available"     ) var available     : Int?             = null,
    @SerializedName("returned"      ) var returned      : Int?             = null,
    @SerializedName("collectionURI" ) var collectionURI : String?          = null,
    @SerializedName("items"         ) var items         : ArrayList<ComicSummary> = arrayListOf(),
)