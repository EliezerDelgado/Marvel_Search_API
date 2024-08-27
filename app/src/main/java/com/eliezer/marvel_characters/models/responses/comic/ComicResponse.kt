package com.eliezer.marvel_characters.models.responses.comic

import com.eliezer.marvel_characters.models.responses.EventList
import com.eliezer.marvel_characters.models.responses.Image
import com.eliezer.marvel_characters.models.responses.SeriesSummary
import com.eliezer.marvel_characters.models.responses.StoryList
import com.eliezer.marvel_characters.models.responses.Url
import com.google.gson.annotations.SerializedName


data class ComicResponse (

  @SerializedName("id"                 ) var id                 : Int?                = null,
  @SerializedName("digitalId"          ) var digitalId          : Int?                = null,
  @SerializedName("title"              ) var title              : String?             = null,
  @SerializedName("issueNumber"        ) var issueNumber        : Double?                = null,
  @SerializedName("variantDescription" ) var variantDescription : String?             = null,
  @SerializedName("description"        ) var description        : String?             = null,
  @SerializedName("modified"           ) var modified           : String?             = null,
  @SerializedName("isbn"               ) var isbn               : String?             = null,
  @SerializedName("upc"                ) var upc                : String?             = null,
  @SerializedName("diamondCode"        ) var diamondCode        : String?             = null,
  @SerializedName("ean"                ) var ean                : String?             = null,
  @SerializedName("issn"               ) var issn               : String?             = null,
  @SerializedName("format"             ) var format             : String?             = null,
  @SerializedName("pageCount"          ) var pageCount          : Int?                = null,
  @SerializedName("textObjects"        ) var textObjects        : ArrayList<TextObject>   = arrayListOf(),
  @SerializedName("resourceURI"        ) var resourceURI        : String?             = null,
  @SerializedName("urls"               ) var urls               : ArrayList<Url>     = arrayListOf(),
  @SerializedName("series"             ) var series             : SeriesSummary?             = SeriesSummary(),
  @SerializedName("variants"           ) var variants           : ArrayList<ComicSummary> = arrayListOf(),
  @SerializedName("collections"        ) var collections        : ArrayList<ComicSummary>   = arrayListOf(),
  @SerializedName("collectedIssues"    ) var collectedIssues    : ArrayList<ComicSummary>   = arrayListOf(),
  @SerializedName("dates"              ) var dates              : ArrayList<ComicDate>    = arrayListOf(),
  @SerializedName("prices"             ) var prices             : ArrayList<ComicPrice>   = arrayListOf(),
  @SerializedName("thumbnail"          ) var thumbnail          : Image?          = Image(),
  @SerializedName("images"             ) var images             : ArrayList<Image>   = arrayListOf(),
  @SerializedName("creators"           ) var creators           : CreatorList?           = CreatorList(),
  @SerializedName("characters"         ) var characters         : CharacterList?         = CharacterList(),
  @SerializedName("stories"            ) var stories            : StoryList?            = StoryList(),
  @SerializedName("events"             ) var events             : EventList?             = EventList()

)