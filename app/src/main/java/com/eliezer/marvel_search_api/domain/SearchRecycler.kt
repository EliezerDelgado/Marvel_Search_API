package com.eliezer.marvel_search_api.domain

import com.eliezer.marvel_search_api.models.SearchEncounter

class SearchRecycler {
    var searchWord = ""
    var itemsContainText = arrayListOf<SearchEncounter>()
    var lastNumText = if(itemsContainText.size>0)
        itemsContainText[itemsContainText.size-1].numText
    else
        0
    var index = -1
    val isInLastPosition get() = itemsContainText.size - 1 == index
    val  isInFirstPosition get() = 0 == index
    val  isNotSetPosition get() = -1 == index

    fun itemsContainTextAddAll(searchEncounters: ArrayList<SearchEncounter>)
    {
        searchEncounters.forEach{
            if(!itemsContainText.contains(it))
                itemsContainText.add(it)
        }
    }
    fun contains(numText : Int)= itemsContainText.run {
           var result = false
            forEach{
                if(it.numText == numText)
                    result = true
            }
            result
        }
    fun get(numText : Int)= itemsContainText.run {
        var result : SearchEncounter? = null
        forEach{
            if(it.numText == numText)
                result = it
        }
        result
    }

}