package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.view.LayoutInflater
import com.eliezer.marvel_characters.core.base.BaseAdapter
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
import com.eliezer.marvel_characters.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_characters.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_characters.domain.SearchRecycler
import com.eliezer.marvel_characters.models.SearchEncounter
import com.eliezer.marvel_characters.models.dataclass.Comic

class CharacterProfileComicsListAdapter(items : ArrayList<Comic>) : BaseAdapter<Comic, ItemComicHorizontalViewHolder>(
    items = items) {
    private val searchRecycler = SearchRecycler()
    fun setComics(listComic : List<Comic>) =
        setListItems(listComic)

    fun fillItemsContainText(text : String)
    {
        searchRecycler.apply {
            itemsContainText = getItemsContain(text)
            itemsContainText.add(0,SearchEncounter(0,0,0,0))
            lastNumText = itemsContainText[itemsContainText.size-1].numText + 1
            itemsContainText.add(lastNumText,SearchEncounter(lastNumText,lastNumText,lastNumText,lastNumText))
        }
    }
    fun nextPosition()
    {
        if(!searchRecycler.isInLastPosition)
            searchRecycler.index++
    }
    fun backPosition()
    {
        if(!searchRecycler.isInFirstPosition)
            searchRecycler.index--
    }
    fun isLastPosition() = searchRecycler.isInLastPosition
    fun isInFirstPosition() = searchRecycler.isInFirstPosition

    override fun setViewHolder(inflater: LayoutInflater): ItemComicHorizontalViewHolder {
        val binding = ItemComicHorizontalBinding.inflate(inflater)
        return ItemComicHorizontalViewHolder(binding)
    }

    override fun addMoreBindViewHolderFunction(holder: ItemComicHorizontalViewHolder, item: Comic) {
        super.addMoreBindViewHolderFunction(holder, item)
        val position = items.indexOf(item)
        val searchEncounter : SearchEncounter? = searchRecycler.get(position)
        if(
            searchEncounter != null
            && position > 0
            && position < searchRecycler.lastNumText
            && searchRecycler.index == searchEncounter.numText
            ) {

            holder.paintText(searchRecycler.searchWord,searchTextResultColor,searchEncounter.position,
                selectSearchTextResultColor)
        }
        else if(
            searchEncounter != null
            && position > 0
            && position < searchRecycler.lastNumText
            )
        {
            holder.paintText(searchRecycler.searchWord, searchTextResultColor,null,
                null)
        }
    }

}