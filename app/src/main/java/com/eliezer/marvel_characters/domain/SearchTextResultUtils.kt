package com.eliezer.marvel_characters.domain

import android.widget.TextView
import com.eliezer.marvel_characters.models.SearchEncounter
import com.eliezer.marvel_characters.models.SearchTextResult

class SearchTextResultUtils {
    companion object{
        fun createSearchTextResult(search:String,listTextView : List<TextView>,startPosition: Int = 0) : SearchTextResult
        {
            val searchTextResult = SearchTextResult()
            var num = startPosition
            searchTextResult.search = search
            listTextView.forEach {
                num++
                if(it.text.contains(search) && search.isNotEmpty())
                    fillOutListSearch(searchTextResult,search,it,num)
            }
            return searchTextResult
        }
        fun createSearchTextResult(search:String,listTextView : HashMap<Int,TextView>,startPosition: Int = 0) : SearchTextResult
        {
            val searchTextResult = SearchTextResult()
            var num = startPosition
            searchTextResult.search = search
            listTextView.forEach {
                num++
                if(it.value.text.contains(search) && search.isNotEmpty())
                    fillOutListSearch(searchTextResult,search,it.key,it.value,num)
            }
            return searchTextResult
        }
        private fun fillOutListSearch(searchTextResult : SearchTextResult ,search: String,textView: TextView, numText : Int) {
            searchTextResult.apply {
                var position = textView.text.indexOf(search)
                var index = 0
                while (position != -1) {
                    val scrollPosition = try {
                        textView.layout.run { getLineTop(getLineForOffset(position)) }
                    }catch (_:Exception){
                        null
                    }
                    encounter.add(
                        SearchEncounter(
                            idTextView = textView.id,
                            scrollPosition = scrollPosition,
                            length = textView.text.length,
                            numText = numText,
                            position = index++)
                    )
                    position = textView.text.indexOf(search,1+position )
                }
            }
        }
        private fun fillOutListSearch(searchTextResult : SearchTextResult ,search: String,id : Int,textView: TextView, numText : Int) {
            searchTextResult.apply {
                var position = textView.text.indexOf(search)
                var index = 0
                while (position != -1) {
                    val scrollPosition = try {
                        textView.layout.run { getLineTop(getLineForOffset(position)) }
                    }catch (_:Exception){
                        null
                    }
                    encounter.add(
                        SearchEncounter(
                            idTextView = id,
                            scrollPosition = scrollPosition,
                            length = textView.text.length,
                            numText = numText,
                            position = index++)
                    )
                    position = textView.text.indexOf(search,1+position )
                }
            }
        }
    }
}