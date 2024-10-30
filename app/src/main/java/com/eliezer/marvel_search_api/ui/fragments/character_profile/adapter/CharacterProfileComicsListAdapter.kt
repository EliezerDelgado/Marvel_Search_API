package com.eliezer.marvel_search_api.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_search_api.core.base.BaseAdapter
import com.eliezer.marvel_search_api.data.configuration.searchTextResultColor
import com.eliezer.marvel_search_api.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_search_api.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_search_api.domain.SearchRecycler
import com.eliezer.marvel_search_api.domain.function.RecyclerAdapterSearchText
import com.eliezer.marvel_search_api.models.SearchTextResult
import com.eliezer.marvel_search_api.models.dataclass.Comic

class CharacterProfileComicsListAdapter(items : ArrayList<Comic>,private val listener :CharacterProfileComicHolderListener?) : BaseAdapter<Comic, ItemComicHorizontalViewHolder>(
    items = items) ,RecyclerAdapterSearchText {
    interface CharacterProfileComicHolderListener
    {
        fun onScroll(position : Int)
    }
    private val searchRecycler = SearchRecycler()

    override fun isLastPosition() = searchRecycler.isInLastPosition
    override fun isInFirstPosition() = searchRecycler.isInFirstPosition

    fun setComics(listComic : List<Comic>)
    {
        addListItems(listComic)
        items.sortBy { it.id }
        fillItemsContainText("")
    }

    override fun fillItemsContainText(text : String)
    {
        searchRecycler.apply {
                index = -1
                searchWord = text
        }
        searchRecycler.itemsContainText.clear()
        resetItemContain()
    }

    private fun resetItemContain() {
        update(0,items.size-1)
    }

    override fun nextPosition()
    {
        if(!searchRecycler.isInLastPosition)
            searchRecycler.index++
        else
            searchRecycler.index = 0
        resetItemContain()
        doScroll()
    }
    override fun backPosition()
    {
        if(!searchRecycler.isNotSetPosition)
            searchRecycler.index--
        else
            searchRecycler.index = 0
        resetItemContain()
        doScroll()
    }
    private fun doScroll()
    {
        val position = getPositionItem(searchRecycler.itemsContainText[searchRecycler.index].id)
        listener?.onScroll(position)
    }

    override fun setViewHolder(inflater: LayoutInflater): ItemComicHorizontalViewHolder {
        val binding = ItemComicHorizontalBinding.inflate(inflater)
        return ItemComicHorizontalViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemComicHorizontalViewHolder, item: Comic) {
        super.addMoreBindViewHolderFunction(holder, item)
        val searchTextResult = createSearchTextResult(holder,item)
        addItemContainTextAddAll(searchTextResult)
        paintSearchResult(searchTextResult,holder,item)
    }


    private fun createSearchTextResult(holder: ItemComicHorizontalViewHolder, item: Comic) : SearchTextResult?
    {

        val sizeBefore = searchRecycler.itemsContainText.size
        val startPosition = if(sizeBefore!=0) searchRecycler.itemsContainText[sizeBefore-1].numText else 0
        return  holder.searchWord(item.id,searchRecycler.searchWord,startPosition)
    }

    private fun addItemContainTextAddAll(searchTextResult: SearchTextResult?) {
        searchTextResult?.also {  searchRecycler.itemsContainTextAddAll(it.encounter)}
        sortSearchRecycler()
    }

    private fun paintSearchResult(searchTextResult: SearchTextResult?,holder: ItemComicHorizontalViewHolder,item: Comic) {
        val idItemIndex = if(searchRecycler.index!=-1) searchRecycler.itemsContainText[searchRecycler.index].id else 0
        searchTextResult?.also {
            if(it.encounter.isNotEmpty() && idItemIndex== item.id ) {
                val index = searchRecycler.run { itemsContainText[index].position }
                holder.setTitleColor(
                    it, searchTextResultColor, index,
                    selectSearchTextResultColor
                )
            } else {
                holder.setTitleColor(it, searchTextResultColor, null, null)
            }
        }
    }

    private fun getPositionItem(idTextView: Int): Int =
        items.run{
            var index = 0
            forEach {
                if (it.id == idTextView)
                    index = items.indexOf(it)
            }
            index
        }

    private fun sortSearchRecycler() {
        searchRecycler.itemsContainText.sortBy {
            it.id
        }
    }

    override fun setPosition(position: Int) {
        if(searchRecycler.index!=position) {
            searchRecycler.index = position
            resetItemContain()
        }
    }
}