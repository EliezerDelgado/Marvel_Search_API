package com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter

import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.base.BaseItemViewHolder
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.databinding.ItemComicBinding
import com.eliezer.marvel_search_api.models.dataclass.Comic
import com.eliezer.marvel_search_api.models.dataclass.Comics
import com.eliezer.marvel_search_api.ui.fragments.comic_list.adapter.ComicsListAdapter.ComicHolderListener

open class ItemComicViewHolder (override var binding: ItemComicBinding) : BaseItemViewHolder<Comic>(binding = binding ) {
    override fun onBindMethodCalled(item: Comic) {
        val t = Thread {
            binding.setVariable(BR.img, loadImageFromWebOperations(item.urlPicture))
        }
        t.start()
    }
    fun itemComicImageButtonFavoriteListener(listener: ()->Unit)= listener.invoke()
}