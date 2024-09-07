package com.eliezer.marvel_characters.models

import androidx.appcompat.widget.AppCompatTextView

data class SearchEncounter(val layout : AppCompatTextView,val position : Int)
{
    val linePosition get() = layout.layout.getLineForOffset(position)
    val scrollPosition get() = layout.layout.getLineTop(linePosition)
}
