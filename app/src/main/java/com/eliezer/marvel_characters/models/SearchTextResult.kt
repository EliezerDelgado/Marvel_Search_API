package com.eliezer.marvel_characters.models

data class SearchTextResult(var search : String = "", val encounter: ArrayList<SearchEncounter> = arrayListOf())
