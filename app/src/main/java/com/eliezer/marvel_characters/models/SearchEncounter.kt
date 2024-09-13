package com.eliezer.marvel_characters.models

data class SearchEncounter(val idTextView : Int,val length :Int,val numText : Int,val position : Int,val scrollPosition: Int?) {
    override fun equals(other: Any?): Boolean {
        other as SearchEncounter
        return other.idTextView == idTextView && other.position ==position
    }

    override fun hashCode(): Int {
        var result = idTextView
        result = 31 * result + length
        result = 31 * result + numText
        result = 31 * result + position
        result = 31 * result + (scrollPosition ?: 0)
        return result
    }
}
