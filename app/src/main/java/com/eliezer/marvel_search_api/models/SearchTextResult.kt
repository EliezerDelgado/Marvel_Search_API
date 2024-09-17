package com.eliezer.marvel_search_api.models

data class SearchTextResult(var search : String = "", val encounter: ArrayList<SearchEncounter> = arrayListOf())
