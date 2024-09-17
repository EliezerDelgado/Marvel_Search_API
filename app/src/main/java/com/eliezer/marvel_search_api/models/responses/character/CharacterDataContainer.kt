package com.eliezer.marvel_search_api.models.responses.character

import com.google.gson.annotations.SerializedName

data class CharacterDataContainer(
    @SerializedName("offset"  ) var offset  : Int?               = null,
    @SerializedName("limit"   ) var limit   : Int?               = null,
    @SerializedName("total"   ) var total   : Int?               = null,
    @SerializedName("count"   ) var count   : Int?               = null,
    @SerializedName("results" ) var results : ArrayList<CharacterResponse> = arrayListOf()
)
