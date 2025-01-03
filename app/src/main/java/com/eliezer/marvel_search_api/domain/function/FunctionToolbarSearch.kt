package com.eliezer.marvel_search_api.domain.function

import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_search_api.data.configuration.searchTextResultColor
import com.eliezer.marvel_search_api.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_search_api.domain.SearchTextResultUtils
import com.eliezer.marvel_search_api.domain.adapter.SearchTexTViewAdapter

interface RecyclerAdapterSearchText{
    fun isInFirstPosition() : Boolean
    fun backPosition()
    fun nextPosition()
    fun setPosition(position : Int)
    fun isLastPosition() : Boolean
    fun fillItemsContainText(text : String)
}

class FunctionToolbarSearch(private var textViews : ArrayList<TextView>,
                            private val scrollView: ScrollView,
                            private val adapter : RecyclerAdapterSearchText,
                            private val recycler : RecyclerView?)
{
    private var searchTextViewAdapter : SearchTexTViewAdapter? = null
    fun searchWordBack() {
        searchTextViewAdapter?.also {
            if (it.getNumRecycler()==-1 || adapter.isInFirstPosition())
                it.backNumLine()
            if(it.getNumRecycler()!=-1)
            {
                setTextViewsColor()
                adapter.backPosition()
            }
            else {
                searchWordTextView()
                moveToLineBack(it.numLine)
            }
        }
    }

    fun searchWordForward() {
        searchTextViewAdapter?.also {
            if (it.getNumRecycler()==-1 || adapter.isLastPosition())
                it.nextNumLine()
            if(it.getNumRecycler()!=-1) {
                setTextViewsColor()
                adapter.nextPosition()
            }
            else {
                searchWordTextView()
                moveToLineForward(it.numLine)
            }
        }
    }

    fun searchText(text: String) {
        setSearchTextViewAdapter(text)
        setTextViewsColor()
        if(text.isNotEmpty()) {
            moveToLineForward(searchTextViewAdapter?.numLine?:0)
        }
        adapter.fillItemsContainText(text)
    }

    fun returnNormalColor() {
        searchTextViewAdapter?.searchText?.search = ""
        adapter.fillItemsContainText("")
        setTextViewsColor()
    }

    private fun  searchWordTextView()
    {
        setTextViewsColor()
        adapter.setPosition(-1)
    }

    private fun setTextViewsColor() {
        searchTextViewAdapter?.apply {
            textViews.forEach{
                setColorSearchTextFor(it, searchTextResultColor,
                    selectSearchTextResultColor
                )
            }
        }
    }

    private fun setSearchTextViewAdapter(text: String) {
        searchTextViewAdapter =
            SearchTexTViewAdapter(
                SearchTextResultUtils.createSearchTextResult(
                    search = text,
                    listTextView = textViews
                )
            )
        recycler?.also {
            searchTextViewAdapter!!.addRecyclerPosition(it.id,4)
        }
    }

    private fun moveToLineBack(numLine: Int) {
        try {
            searchTextViewAdapter?.searchText?.apply {
                if (encounter.size > 0)
                    encounter[numLine].apply {
                        val sc = scrollPosition?.let { it - 80 } ?: 0
                        scrollView.scrollTo(0, sc)
                    }
            }
        }
            catch (_ :  Exception){}
    }
    private fun moveToLineForward(numLine: Int) {
        try {
            searchTextViewAdapter?.searchText?.apply {
                if (encounter.size > 0)
                    encounter[numLine].apply {
                        val sc = scrollPosition?.let { it + 80 } ?: 0
                        scrollView.scrollTo(0, sc)
                    }
            }
        }
        catch (_ :  Exception){}
    }
}