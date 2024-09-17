package com.eliezer.marvel_search_api.models.responses.comic

import com.google.gson.annotations.SerializedName

data class CharacterSummary (

@SerializedName("resourceURI"  ) var resourceURI  : String?               = null,
@SerializedName("name"   ) var name   : String?               = null,
@SerializedName("role"   ) var role   : String?               = null
)
