package com.eliezer.marvel_characters.data.expand

fun String.indexOfEncounter(string : String,index : Int) : Int
{
    var position = 0
    for (i in 0..index) {
        position = indexOf(string, position)
        position+1
    }
    return position
}