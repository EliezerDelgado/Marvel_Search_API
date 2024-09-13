package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
import com.eliezer.marvel_characters.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_characters.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_characters.domain.SearchRecycler
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comic

class CharacterProfileComicsListAdapter(items : ArrayList<Comic>,private val listener :CharacterProfileComicHolderListener) : BaseAdapter<Comic, ItemComicHorizontalViewHolder>(
    items = items) {
    interface CharacterProfileComicHolderListener
    {
        fun onScroll(position : Int)
    }
    private val searchRecycler = SearchRecycler()
    fun setComics(listComic : List<Comic>)
    {
        setListItems(listComic)
        items.sortBy { it.id }
        fillItemsContainText("")
    }

    fun fillItemsContainText(text : String)
    {
        searchRecycler.apply {
                index = -1
                searchWord = text
        }
        searchRecycler.itemsContainText.clear()
        resetItemContain()
    }

    private fun resetItemContain() {
        update()
    }

    fun nextPosition()
    {
        if(!searchRecycler.isInLastPosition)
            searchRecycler.index++
        else
            searchRecycler.index = 0
        resetItemContain()
        val position = getPositionItem(searchRecycler.itemsContainText[searchRecycler.index].idTextView)
        listener.onScroll(position)
    }
    fun backPosition()
    {
        if(!searchRecycler.isNotSetPosition)
            searchRecycler.index--
        else
            searchRecycler.index = 0
        resetItemContain()
        val position = getPositionItem(searchRecycler.itemsContainText[searchRecycler.index].idTextView)
        listener.onScroll(position)
    }
    fun isLastPosition() = searchRecycler.isInLastPosition
    fun isInFirstPosition() = searchRecycler.isInFirstPosition

    override fun setViewHolder(inflater: LayoutInflater): ItemComicHorizontalViewHolder {
        val binding = ItemComicHorizontalBinding.inflate(inflater)
        return ItemComicHorizontalViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemComicHorizontalViewHolder, item: Comic) {
        super.addMoreBindViewHolderFunction(holder, item)
        val sizeBefore = searchRecycler.itemsContainText.size
        val startPosition = if(sizeBefore!=0) searchRecycler.itemsContainText[sizeBefore-1].numText else 0
        val searchTextResult =holder.searchWord(holder,item.id,searchRecycler.searchWord,startPosition)
        searchTextResult?.also {
            if(it.encounter.isNotEmpty()) {
                searchRecycler.itemsContainTextAddAll(it.encounter)
                sortSearchRecycler()
                val idItemIndex = if(searchRecycler.index!=-1) searchRecycler.itemsContainText[searchRecycler.index].idTextView else 0
                if (idItemIndex== item.id ) {
                    val index = searchRecycler.run { itemsContainText[index].position }
                    holder.setTitleColor(
                        it, searchTextResultColor, index,
                        selectSearchTextResultColor
                    )
                } else {
                    holder.setTitleColor(it, searchTextResultColor, null, null)
                }
            }
            else{
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
            it.idTextView
        }
    }

    fun setPosition(i: Int) {
        if(searchRecycler.index!=i) {
            searchRecycler.index = i
            resetItemContain()
        }
    }
}