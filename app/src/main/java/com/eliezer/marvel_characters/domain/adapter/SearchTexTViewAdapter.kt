package com.eliezer.marvel_characters.domain.adapter

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.eliezer.marvel_characters.data.expand.indexOfEncounter
import com.eliezer.marvel_characters.models.SearchTextResult

class SearchTexTViewAdapter(var searchText : SearchTextResult = SearchTextResult()) {
    private var  _numLine = 0
    val numLine get() = _numLine%(searchText.encounter.size-1)

    fun nextNumLine() {
        ++_numLine
    }
    fun backNumLine() {
        --_numLine
    }

    fun setColorSearchTextFor(textView: AppCompatTextView, @ColorInt normalColor: Int, @ColorInt selectColor : Int) {
        if (textView.text.contains(searchText.search)) {
            textView.apply {
                text =colorAllLinesText(id,text.toString(),  normalColor,
                    selectColor
                )
            }
        }
        else
            setColorAllText(textView, normalColor)
    }
    fun setColorAllText(textView: AppCompatTextView,@ColorInt defaultColor: Int){
        val spannableStringBuilder = SpannableStringBuilder(textView.text)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(defaultColor),
            0,
            textView.text.length-1,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableStringBuilder
    }

    private fun colorUnderLineText(spannableStringBuilder : SpannableStringBuilder, numLine: Int, text : String, @ColorInt color: Int) : SpannableStringBuilder {

        val position = text.indexOfEncounter(searchText.search,searchText.encounter[numLine].position)
        val intRange = IntRange(position,position+searchText.search.length-1)
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(color),
            intRange.first,
            intRange.last,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableStringBuilder
    }

    private fun colorAllLinesText(idTextView: Int, text : String, @ColorInt normalColor: Int, @ColorInt selectColor : Int) : SpannableStringBuilder {
        var highlighted = SpannableStringBuilder(text)
        for (i in 0..<searchText.encounter.size)
        {
            highlighted = if (i == numLine &&  idTextView == searchText.encounter[i].idTextView)
                colorUnderLineText(highlighted,i,text,normalColor)
            else if (idTextView == searchText.encounter[i].idTextView)
                colorUnderLineText(highlighted,i,text,selectColor)
            else
                highlighted
        }
        return highlighted
    }
}