package com.eliezer.marvel_characters.ui.fragments.comic_list.functionImp

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentComicsListBinding
import com.eliezer.marvel_characters.domain.actions.NavigationMainActions
import com.eliezer.marvel_characters.domain.listener.MyOnScrolled
import com.eliezer.marvel_characters.models.dataclass.Comic
import com.eliezer.marvel_characters.models.dataclass.Comics
import com.eliezer.marvel_characters.ui.fragments.comic_list.ComicsListFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.comic_list.adapter.ComicsListAdapter
import com.eliezer.marvel_characters.ui.fragments.comic_list.viewmodel.ComicsListViewModel


class ComicsListFunctionImplement (
    private val binding: FragmentComicsListBinding,
    private val navigationMainActions: NavigationMainActions,
    private val  viewModel: ComicsListViewModel,
    private val getComicsRepository : GetComicsRepository,
    private val owner : LifecycleOwner
) : ComicsListAdapter.ComicHolderListener{
    private var adapter: ComicsListAdapter? = null
    private var title : String? = null
    private val myOnScrolled = MyOnScrolled { getListComics()}

    fun setAdapter() {
        adapter = ComicsListAdapter(arrayListOf(),this)
        binding.comicsListRecyclerView.setHasFixedSize(true)
        binding.comicsListRecyclerView.adapter = adapter
        binding.comicsListRecyclerView.addOnScrollListener(myOnScrolled)
    }

    fun getListComicsRepository()
    {
        title?.also {
            val comics = getComicsRepository.getListRepository(it)
            setComicsList(comics)
        }
    }


    private fun setComicsList(comics: Comics?) =
        adapter?.setComics(comics?.listComics ?: emptyList())

    override fun onComicItemClickListener(comic: Comic) {
        navigationMainActions.actionComicsListFragmentToComicDescriptionFragment(comic =comic)
    }

    fun getIntentExtras(arguments: Bundle) {
        title = ComicsListFragmentArgs.fromBundle(arguments).argSearchComic
    }


    private fun setObservesVM() {
        viewModel.listComic.observe(owner,::setListComics)
    }

    private fun getListComics() {
        binding.comicsListRecyclerView.removeOnScrollListener(myOnScrolled)
        val comics = getComicsRepository.getListRepository(title!!)
        if(comics==null || comics.total > comics.listComics.size)
            searchListComics()
        else if (adapter!!.isListEmpty())
            setListComics(comics)

    }
    private fun searchListComics() {
        setObservesVM()
        viewModel.searchComicsList(title!!)
    }

    private fun setListComics(characters: Comics?) {
        val position = myOnScrolled.position
        characters?.apply {
            if (listComics.isNotEmpty())
                adapter?.setComics(listComics)

        }
        resetRecyclerView()
        setNotObservesVM()
        binding.comicsListRecyclerView.scrollToPosition(position)
    }

    private fun setNotObservesVM() {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }
    private fun resetRecyclerView() {
        binding.comicsListRecyclerView.addOnScrollListener(myOnScrolled)
    }

}