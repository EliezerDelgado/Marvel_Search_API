package com.eliezer.marvel_search_api.core.utils

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.eliezer.marvel_search_api.data.expand.indexOfEncounter

fun colorText(spannableStringBuilder: SpannableStringBuilder,string : String,color: Int,index : Int) : SpannableStringBuilder
{
    val position = spannableStringBuilder.toString().indexOfEncounter(string,index)
    if(position!=-1) {
        val intRange = IntRange(position, position + string.length)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(color),
            intRange.first,
            intRange.last,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannableStringBuilder
}