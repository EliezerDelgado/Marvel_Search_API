package com.eliezer.marvel_characters.ui.fragments.comic_description.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
import com.eliezer.marvel_characters.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_characters.databinding.ItemCharacterHorizontalBinding
import com.eliezer.marvel_characters.domain.SearchRecycler
import com.eliezer.marvel_characters.domain.function.RecyclerAdapterSearchText
import com.eliezer.marvel_characters.models.SearchTextResult
import com.eliezer.marvel_characters.models.dataclass.Character

class ComicDescriptionCharacterListAdapter(items : ArrayList<Character>,private val listener : ComicDescriptionComicHolderListener) : BaseAdapter<Character, ItemCharacterHorizontalViewHolder>(
    items = items), RecyclerAdapterSearchText {
    interface ComicDescriptionComicHolderListener
    {
        fun onScroll(position : Int)
    }
    private val searchRecycler = SearchRecycler()

    override fun isLastPosition() = searchRecycler.isInLastPosition
    override fun isInFirstPosition() = searchRecycler.isInFirstPosition
    fun setCharacters(listCharacter : List<Character>)
    {
        setListItems(listCharacter)
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
        update()
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
        listener.onScroll(position)
    }

    override fun setViewHolder(inflater: LayoutInflater): ItemCharacterHorizontalViewHolder {
        val binding = ItemCharacterHorizontalBinding.inflate(inflater)
        return ItemCharacterHorizontalViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemCharacterHorizontalViewHolder, item: Character) {
        super.addMoreBindViewHolderFunction(holder, item)
        val searchTextResult = createSearchTextResult(holder,item)
        addItemContainTextAddAll(searchTextResult)
        paintSearchResult(searchTextResult,holder,item)
    }
    private fun createSearchTextResult(holder: ItemCharacterHorizontalViewHolder, item: Character) : SearchTextResult?
    {

        val sizeBefore = searchRecycler.itemsContainText.size
        val startPosition = if(sizeBefore!=0) searchRecycler.itemsContainText[sizeBefore-1].numText else 0
        return  holder.searchWord(item.id,searchRecycler.searchWord,startPosition)
    }

    private fun addItemContainTextAddAll(searchTextResult: SearchTextResult?) {
        searchTextResult?.also {  searchRecycler.itemsContainTextAddAll(it.encounter)}
        sortSearchRecycler()
    }

    private fun paintSearchResult(searchTextResult: SearchTextResult?, holder: ItemCharacterHorizontalViewHolder, item: Character) {
        val idItemIndex = if(searchRecycler.index!=-1) searchRecycler.itemsContainText[searchRecycler.index].id else 0
        searchTextResult?.also {
            if(it.encounter.isNotEmpty() && idItemIndex== item.id ) {
                val index = searchRecycler.run { itemsContainText[index].position }
                holder.setNameColor(
                    it, searchTextResultColor, index,
                    selectSearchTextResultColor
                )
            } else {
                holder.setNameColor(it, searchTextResultColor, null, null)
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