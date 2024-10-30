package com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter

import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.base.BaseItemViewHolder
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.databinding.ItemComicBinding
import com.eliezer.marvel_search_api.models.dataclass.Comic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class ItemComicViewHolder (override var binding: ItemComicBinding) : BaseItemViewHolder<Comic>(binding = binding ) {


    override fun onBindMethodCalled(item: Comic) {
        CoroutineScope(Dispatchers.IO).launch {
            val drawable = loadImageFromWebOperations(item.urlPicture)
            binding.setVariable(BR.img, drawable)
        }.start()
    }
    fun itemComicImageButtonFavoriteListener(listener: ()->Unit)=
        binding.itemComicImageButtonFavorite.setOnClickListener{
            listener.invoke()
        }
}