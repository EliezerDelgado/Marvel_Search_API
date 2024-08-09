package com.eliezer.marvel_characters.models.responses.character

import com.eliezer.marvel_characters.models.responses.EventList
import com.eliezer.marvel_characters.models.responses.Image
import com.eliezer.marvel_characters.models.responses.StoryList
import com.eliezer.marvel_characters.models.responses.Url
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id"          ) var id          : Int?            = null,
    @SerializedName("name"        ) var name        : String?         = null,
    @SerializedName("description" ) var description : String?         = null,
    @SerializedName("modified"    ) var modified    : String?         = null,
    @SerializedName("thumbnail"   ) var thumbnail   : Image?      = Image(),
    @SerializedName("resourceURI" ) var resourceURI : String?         = null,
    @SerializedName("comics"      ) var comics      : ComicList?         = ComicList(),
    @SerializedName("series"      ) var series      : SeriesList?         = SeriesList(),
    @SerializedName("stories"     ) var stories     : StoryList?        = StoryList(),
    @SerializedName("events"      ) var events      : EventList?         = EventList(),
    @SerializedName("urls"        ) var urls        : ArrayList<Url> = arrayListOf()

)