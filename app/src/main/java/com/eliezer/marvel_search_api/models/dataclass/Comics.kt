package com.eliezer.marvel_search_api.models.dataclass

data class Comics(var total : Int , val listComics: ArrayList<Comic>)
{

    constructor() : this(0, ArrayList())
}
