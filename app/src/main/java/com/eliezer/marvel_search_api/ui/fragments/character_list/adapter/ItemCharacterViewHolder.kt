package com.eliezer.marvel_search_api.ui.fragments.character_list.adapter

import android.graphics.BitmapFactory
import com.eliezer.marvel_search_api.BR
import com.eliezer.marvel_search_api.core.base.BaseItemViewHolder
import com.eliezer.marvel_search_api.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_search_api.core.utils.toBitmap
import com.eliezer.marvel_search_api.databinding.ItemCharacterBinding
import com.eliezer.marvel_search_api.models.dataclass.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream

open class ItemCharacterViewHolder(override var binding: ItemCharacterBinding) : BaseItemViewHolder<Character>(binding = binding ) {
    override fun onBindMethodCalled(item: Character) {
        item.image?.also {
            binding.itemCharacterImageViewPhoto.setImageBitmap(it.toBitmap())
        }?: also {
            CoroutineScope(Dispatchers.IO).launch {
                val drawable = loadImageFromWebOperations(item.urlPicture)
                binding.setVariable(BR.img, drawable)
            }.start()
        }
    }
    fun itemCharacterImageButtonFavoriteListener(listener: ()->Unit) =
        binding.itemCharacterImageButtonFavorite.setOnClickListener {
            listener.invoke()
        }
}