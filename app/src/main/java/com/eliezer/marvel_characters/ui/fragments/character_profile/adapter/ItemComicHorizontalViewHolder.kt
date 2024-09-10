package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.text.SpannableStringBuilder
import androidx.viewbinding.ViewBinding
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_characters.domain.SearchTextResultUtils
import com.eliezer.marvel_characters.domain.adapter.SearchTexTViewAdapter
import com.eliezer.marvel_characters.models.SearchEncounter
import com.eliezer.marvel_characters.models.SearchTextResult
import com.eliezer.marvel_characters.models.dataclass.Comic

class ItemComicHorizontalViewHolder(override var binding: ItemComicHorizontalBinding) : BaseItemViewHolder<Comic>(binding = binding ) {
    override fun onBindMethodCalled(item: Comic) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }

    fun paintText(searchWord: String,color : Int,selectPosition: Int?, selectColor : Int?) {
        val text = binding.itemComicHorizontalTitle.text.toString()
        var spannableStringBuilder = SpannableStringBuilder(text)
        val searchTextViewAdapter = if(text.isNotEmpty())
            SearchTexTViewAdapter(
                SearchTextResultUtils.createSearchTextResult(
                    text,
                    listOf(
                        binding.itemComicHorizontalTitle
                    )
                )
            )
        else
            SearchTexTViewAdapter(SearchTextResult())
        selectPosition?.also {
            searchTextViewAdapter.setNumLine(it)
        }
        searchTextViewAdapter.setColorSearchTextFor(binding.itemComicHorizontalTitle,color,selectColor)
    }
}