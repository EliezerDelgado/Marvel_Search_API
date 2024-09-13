package com.eliezer.marvel_characters.domain.adapter

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.eliezer.marvel_characters.data.expand.indexOfEncounter
import com.eliezer.marvel_characters.models.SearchEncounter
import com.eliezer.marvel_characters.models.SearchTextResult

class SearchTexTViewAdapter(var searchText : SearchTextResult = SearchTextResult()) {
    private var  _numLine = 0
    val numLine get() = _numLine%(searchText.encounter.size)
    private val recyclersPositions = arrayListOf<Int>()

    fun setNumLine(num : Int)
    {
        _numLine = num
    }


    fun nextNumLine() {
            ++_numLine
    }
    fun backNumLine() {
            --_numLine
        if(_numLine<0)
            _numLine = searchText.encounter.size -1
    }
    fun getNumRecycler() : Int =
        recyclersPositions.indexOf(numLine)

    fun setColorSearchTextFor(textView: AppCompatTextView, @ColorInt normalColor: Int, @ColorInt selectColor : Int?) {
        textView.apply {
            text =colorAllLinesText(id,text.toString(),  normalColor,
                selectColor
            )
        }
    }

    fun addRecyclerPosition(idRecycler : Int, positionText : Int)
    {
        var index =searchText.encounter.size
        for (i in 0..<searchText.encounter.size)
        {
            if(searchText.encounter[i].numText == positionText) {
                index = i
                break
            }
        }
        searchText.encounter.add(index, SearchEncounter(idRecycler,positionText,0,0,null))
        recyclersPositions.add(index)
    }

    private fun colorUnderLineText(spannableStringBuilder : SpannableStringBuilder, line: Int, text : String, @ColorInt color: Int) : SpannableStringBuilder {
        val pos = searchText.encounter[line].position
        val position = text.indexOfEncounter(searchText.search,pos)
        if(position!=-1) {
            val intRange = IntRange(position, position + searchText.search.length)
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(color),
                intRange.first,
                intRange.last,
                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableStringBuilder
    }

    private fun colorAllLinesText(idTextView: Int, text : String, @ColorInt normalColor: Int, @ColorInt selectColor : Int?) : SpannableStringBuilder {
        var spannableStringBuilder = SpannableStringBuilder(text)
        for (line in 0..<searchText.encounter.size)
        {
            spannableStringBuilder = if (line == numLine &&  idTextView == searchText.encounter[line].idTextView && selectColor !=null)
                colorUnderLineText(spannableStringBuilder,line,text,selectColor)
            else if (idTextView == searchText.encounter[line].idTextView)
                colorUnderLineText(spannableStringBuilder,line,text,normalColor)
            else
                spannableStringBuilder
        }
        return spannableStringBuilder
    }
}