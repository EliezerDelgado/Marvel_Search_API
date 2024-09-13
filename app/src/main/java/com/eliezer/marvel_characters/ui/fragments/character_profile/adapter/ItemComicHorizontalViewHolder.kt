package com.eliezer.marvel_characters.ui.fragments.character_profile.adapter

import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.colorText
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
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

    fun searchWord(holder: ItemComicHorizontalViewHolder,idItem:Int,searchWord: String?,startPosition : Int) : SearchTextResult?{
        var searchTextResult : SearchTextResult?= null
        val hashMapItem = HashMap<Int,TextView>()
        hashMapItem[idItem] = binding.itemComicHorizontalTitle
        searchWord?.also {
                val text = holder.binding.itemComicHorizontalTitle.text.toString()
                searchTextResult = if (text.isNotEmpty()) {
                    SearchTextResultUtils.createSearchTextResult(
                        it,
                        hashMapItem,
                        startPosition
                    )
                } else
                    SearchTextResult()

        }
        return searchTextResult
    }

    fun setTitleColor(
        searchTextResult: SearchTextResult,
        intColor: Int,
        selectIndex: Int?,
        intSelectColor: Int?
    ) {
        val text = binding.itemComicHorizontalTitle.text.toString()
        var spannableStringBuilder = SpannableStringBuilder(text)
        for (i in 0..searchTextResult.encounter.size)
        {
            if(selectIndex== null || selectIndex != i)
                spannableStringBuilder = colorText(spannableStringBuilder,searchTextResult.search,intColor,i)
            else if(intSelectColor!=null)
                spannableStringBuilder = colorText(spannableStringBuilder,searchTextResult.search,intSelectColor,i)
        }
        binding.itemComicHorizontalTitle.text = spannableStringBuilder
    }
}