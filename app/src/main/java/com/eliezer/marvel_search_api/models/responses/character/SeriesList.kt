package com.eliezer.marvel_search_api.models.responses.character

import com.eliezer.marvel_search_api.models.responses.SeriesSummary
import com.google.gson.annotations.SerializedName

data class SeriesList (

    @SerializedName("available"     ) var available     : Int?              = null,
    @SerializedName("returned"      ) var returned      : Int?              = null,
    @SerializedName("collectionURI" ) var collectionURI : String?           = null,
    @SerializedName("items"         ) var items         : ArrayList<SeriesSummary> = arrayListOf()

)
