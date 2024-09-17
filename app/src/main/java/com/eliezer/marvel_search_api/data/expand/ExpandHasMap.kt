package com.eliezer.marvel_search_api.data.expand

fun <T,S> HashMap<T,S>.getKey(number : Int) : T? {
    val size = keys.size
    if(size>0)
        return ArrayList(keys)[number]
    else
        return null
}

fun <T,S> HashMap<T,S>.getValue(number : Int) = get(getKey(number))