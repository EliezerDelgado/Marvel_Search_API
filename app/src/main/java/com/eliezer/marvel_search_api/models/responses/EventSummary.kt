package com.eliezer.marvel_search_api.models.responses

import com.google.gson.annotations.SerializedName

class EventSummary  (
    @SerializedName("resourceURI"  ) var resourceURI  : String?               = null,
    @SerializedName("name"   ) var name   : String?               = null
)
