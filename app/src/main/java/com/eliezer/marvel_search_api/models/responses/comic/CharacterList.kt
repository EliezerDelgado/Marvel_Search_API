package com.eliezer.marvel_search_api.models.responses.comic

import com.google.gson.annotations.SerializedName

data class CharacterList (

    @SerializedName("available"  ) var available  : Int?               = null,
    @SerializedName("returned"   ) var returned   : Int?               = null,
    @SerializedName("collectionURI"   ) var collectionURI   : String?               = null,
    @SerializedName("items" ) var items : ArrayList<CharacterSummary> = arrayListOf())
