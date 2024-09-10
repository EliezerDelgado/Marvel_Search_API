package com.eliezer.marvel_characters.domain

import com.eliezer.marvel_characters.models.SearchEncounter

class SearchRecycler {
    var searchWord = ""
    var itemsContainText = arrayListOf <SearchEncounter>()
    var lastNumText = 0
    var index = 1
    var isInLastPosition = lastNumText == index
        private set
    var  isInFirstPosition = 0 == index
        private set

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