package com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp

import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.comic_list.adapter.ComicsListAdapter

class ComicsListFunctionImplement (
    private val binding: FragmentComicsListBinding,
    private val getComicsRepository : GetComicsRepository
) {
    private var adapter: ComicsListAdapter? = null

    fun setAdapter() {
        adapter = ComicsListAdapter(arrayListOf())
        binding.comicsListRecyclerView.setHasFixedSize(true)
        binding.comicsListRecyclerView.adapter = adapter
    }

    fun getListComicsRepository()
    {
        val comics = getComicsRepository.getListRepository()
        setComicsList(comics)
    }


    private fun setComicsList(comics: List<Comic>?) =
        adapter?.setComics(comics ?: emptyList())
}