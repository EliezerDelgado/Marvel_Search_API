package com.eliezer.marvel_characters.ui.fragments.comic_description.adapter

import android.text.SpannableStringBuilder
import android.widget.TextView
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.base.BaseItemViewHolder
import com.eliezer.marvel_characters.core.utils.colorText
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.databinding.ItemCharacterHorizontalBinding
import com.eliezer.marvel_characters.domain.SearchTextResultUtils
import com.eliezer.marvel_characters.models.SearchTextResult
import com.eliezer.marvel_characters.models.dataclass.Character

class ItemCharacterHorizontalViewHolder (override val binding: ItemCharacterHorizontalBinding) : BaseItemViewHolder<Character>(binding = binding ) {
    override fun onBindMethodCalled(item: Character) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }
    fun searchWord(idItem:Int, searchWord: String?, startPosition : Int) : SearchTextResult?{
        var searchTextResult : SearchTextResult?= null
        val hashMapItem = HashMap<Int, TextView>()
        hashMapItem[idItem] = binding.itemCharacterHorizontalName
        searchWord?.also {
            val text = binding.itemCharacterHorizontalName.text.toString()
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

    fun setNameColor(
        searchTextResult: SearchTextResult,
        intColor: Int,
        selectIndex: Int?,
        intSelectColor: Int?
    ) {
        val text = binding.itemCharacterHorizontalName.text.toString()
        var spannableStringBuilder = SpannableStringBuilder(text)
        for (i in 0..searchTextResult.encounter.size)
        {
            if(selectIndex== null || selectIndex != i)
                spannableStringBuilder = colorText(spannableStringBuilder,searchTextResult.search,intColor,i)
            else
                intSelectColor?.also {  spannableStringBuilder = colorText(spannableStringBuilder,searchTextResult.search,it,i)}
        }
        binding.itemCharacterHorizontalName.text = spannableStringBuilder
    }
}