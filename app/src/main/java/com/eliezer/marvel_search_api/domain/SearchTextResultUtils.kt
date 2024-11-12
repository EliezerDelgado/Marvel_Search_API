package com.eliezer.marvel_search_api.domain

import android.widget.TextView
import com.eliezer.marvel_search_api.models.SearchEncounter
import com.eliezer.marvel_search_api.models.SearchTextResult

object SearchTextResultUtils {
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
        private fun fillOutListSearch(searchTextResult : SearchTextResult ,search: String,textView: TextView, numText : Int) =
            fillOutListSearch(searchTextResult,search,textView.id,textView,numText)

        private fun fillOutListSearch(searchTextResult : SearchTextResult ,search: String,id : Int,textView: TextView, numText : Int) {
            searchTextResult.apply {
                var position = textView.text.indexOf(search)
                var index = 0
                while (position != -1) {
                    val scrollPosition = try {
                        textView.layout.run { getLineTop(getLineForOffset(position))} + 150
                    }catch (_:Exception){
                        null
                    }
                    encounter.add(
                        SearchEncounter(
                            id = id,
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