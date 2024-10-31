package com.eliezer.marvel_search_api.ui.fragments.character_profile.adapter

import android.text.SpannableStringBuilder
import android.widget.TextView
import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.base.BaseItemViewHolder
import com.eliezer.marvel_search_api.core.utils.colorText
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.core.utils.toBitmap
import com.eliezer.marvel_search_api.databinding.ItemComicHorizontalBinding
import com.eliezer.marvel_search_api.domain.SearchTextResultUtils
import com.eliezer.marvel_search_api.models.SearchTextResult
import com.eliezer.marvel_search_api.models.dataclass.Comic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemComicHorizontalViewHolder(override var binding: ItemComicHorizontalBinding) : BaseItemViewHolder<Comic>(binding = binding ) {
    override fun onBindMethodCalled(item: Comic) {
        item.image?.also {
            binding.itemComicHorizontalImage.setImageBitmap(it.toBitmap())
        }?: also {
            CoroutineScope(Dispatchers.IO).launch {
                binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
            }.start()
        }
    }
    fun searchWord(idItem:Int,searchWord: String?,startPosition : Int) : SearchTextResult?{
        var searchTextResult : SearchTextResult?= null
        val hashMapItem = HashMap<Int,TextView>()
        hashMapItem[idItem] = binding.itemComicHorizontalTitle
        searchWord?.also {
                val text = binding.itemComicHorizontalTitle.text.toString()
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
            else
                intSelectColor?.also {  spannableStringBuilder = colorText(spannableStringBuilder,searchTextResult.search,it,i)}
        }
        binding.itemComicHorizontalTitle.text = spannableStringBuilder
    }
}