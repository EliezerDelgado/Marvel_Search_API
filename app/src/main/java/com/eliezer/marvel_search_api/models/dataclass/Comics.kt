package com.eliezer.marvel_search_api.models.dataclass

import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperationsBitmap
import com.eliezer.marvel_search_api.core.utils.toByteArray

data class Comics(
    var total : Int,
    var search : String,
    val listComics: ArrayList<Comic>
)
{

    constructor() : this(0,"", ArrayList())

    fun setImageComics() {
        listComics.forEach {
            if(it.image==null)
            {
                val bitmap = loadImageFromWebOperationsBitmap(it.urlPicture)
                bitmap?.also { bm->
                    it.image = bm.toByteArray()
                }
            }
        }
    }
}
