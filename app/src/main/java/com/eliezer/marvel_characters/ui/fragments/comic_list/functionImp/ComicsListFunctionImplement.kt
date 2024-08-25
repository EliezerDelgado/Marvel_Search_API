package com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.domain.actions.NavigationMainActions
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.ui.fragments.comic_list.ComicsListFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.comic_list.adapter.ComicsListAdapter


class ComicsListFunctionImplement (
    private val binding: FragmentComicsListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val getComicsRepository : GetComicsRepository
) : ComicsListAdapter.ComicHolderListener{
    private var adapter: ComicsListAdapter? = null
    private var name : String? = null

    fun setAdapter() {
        adapter = ComicsListAdapter(arrayListOf(),this)
        binding.comicsListRecyclerView.setHasFixedSize(true)
        binding.comicsListRecyclerView.adapter = adapter
        binding.comicsListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition() + 1
                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount > 0 && totalItemCount == lastVisible) {

                }
            }
        });
    }

    fun getListComicsRepository()
    {
        name?.also {
            val comics = getComicsRepository.getListRepository(it)
            setComicsList(comics?.listComics)
        }
    }


    private fun setComicsList(comics: List<Comic>?) =
        adapter?.setComics(comics ?: emptyList())

    override fun onComicItemClickListener(comic: Comic) {
        navigationMainActions.actionComicsListFragmentToComicDescriptionFragment(comic =comic)
    }

    fun getIntentExtras(arguments: Bundle) {
        name = ComicsListFragmentArgs.fromBundle(arguments).argSearchComic
    }
}