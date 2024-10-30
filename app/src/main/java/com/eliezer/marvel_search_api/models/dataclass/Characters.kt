package com.eliezer.marvel_search_api.models.dataclass

data  class Characters(
    var total : Int,
    var search : String ,
    val listCharacters: ArrayList<Character>
)
{
    constructor() : this(0,"", ArrayList())
}