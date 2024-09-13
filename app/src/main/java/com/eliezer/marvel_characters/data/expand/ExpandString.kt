package com.eliezer.marvel_characters.data.expand

fun String.indexOfEncounter(string : String,index : Int) : Int
{
    var position = indexOf(string)
    for (i in 0..<index) {
        ++position
        position = indexOf(string, position)
    }
    return position
}