package com.eliezer.marvel_search_api.data.expand

fun <T,S> HashMap<T,S>.getKey(number : Int) : T? {
    val size = keys.size
    return if(size>0)
        ArrayList(keys)[number]
    else
        null
}

fun <T,S> HashMap<T,S>.getValue(number : Int) = get(getKey(number))