package com.eliezer.marvel_characters.domain

import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.eliezer.marvel_characters.models.SearchEncounter
import com.eliezer.marvel_characters.models.SearchTextResult

class SearchTextResultUtils {
    companion object{
        fun createSearchTextResult(search:String,listTextView : List<TextView>) : SearchTextResult
        {
            val searchTextResult = SearchTextResult()
            var num = 0
            searchTextResult.search = search
            listTextView.forEach {
                num++
                if(it.text.contains(search) && search.isNotEmpty())
                    fillOutListSearch(searchTextResult,search,it,num)
            }
            return searchTextResult
        }
        private fun fillOutListSearch(searchTextResult : SearchTextResult ,search: String,textView: TextView, numText : Int) {
            searchTextResult.apply {
                var position = textView.text.indexOf(search, 0)
                var index = 0
                while (position != -1) {
                    val scrollPosition = textView.layout.run { getLineTop(getLineForOffset(position)) }
                    encounter.add(
                        SearchEncounter(
                            idTextView = textView.id,
                            scrollPosition = scrollPosition,
                            numText = numText,
                            position = index++)
                    )
                    position = textView.text.indexOf(search,1+position )
                }
            }
        }
    }
}