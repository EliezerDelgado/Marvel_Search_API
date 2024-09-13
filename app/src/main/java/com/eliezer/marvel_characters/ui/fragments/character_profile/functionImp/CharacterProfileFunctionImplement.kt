package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
import com.eliezer.marvel_characters.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.domain.SearchTextResultUtils
import com.eliezer.marvel_characters.domain.adapter.SearchTexTViewAdapter
import com.eliezer.marvel_characters.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_characters.models.SearchTextResult
import com.eliezer.marvel_characters.models.dataclass.Character
import com.eliezer.marvel_characters.models.dataclass.Comics
import com.eliezer.marvel_characters.ui.fragments.character_profile.CharacterProfileFragmentArgs
import com.eliezer.marvel_characters.ui.fragments.character_profile.adapter.CharacterProfileComicsListAdapter
import com.eliezer.marvel_characters.ui.fragments.character_profile.viewmodel.CharacterProfileViewModel

class CharacterProfileFunctionImplement(
    private val binding: FragmentCharacterProfileBinding,
    private val viewModel: CharacterProfileViewModel,
    private val getComicsRepository : GetComicsRepository,
    private val owner : LifecycleOwner
) : CharacterProfileComicsListAdapter.CharacterProfileComicHolderListener {
    private var character: Character? = null
    private var adapter: CharacterProfileComicsListAdapter? = null
    private val myOnScrolledListener = MyOnScrolledListener { getListComics() }
    private var searchTextViewAdapter : SearchTexTViewAdapter? = null

    fun setBindingVariable() {
        binding.setVariable(BR.item, character)
        val t = Thread {
            character?.urlPicture.also {
                binding.setVariable(
                    BR.img,
                    loadImageFromWebOperations(it)
                )
            }
        }
        t.start()
    }

    private fun setObservesVM() {
        viewModel.listComic.observe(owner, ::setListComics)
    }

    fun getListComics() {
        binding.characterProfileRecyclerViewComics.removeOnScrollListener(myOnScrolledListener)
        val comics = getComicsRepository.getListRepository(character?.id.toString())
        if (comics == null || comics.total > comics.listComics.size)
            searchComics()
        else if (adapter!!.isListEmpty())
            setListComics(comics)
    }

    private fun searchComics() {
        character?.id?.also {
            setObservesVM()
            viewModel.searchComicsList(it)
        }
    }

    private fun setListComics(comics: Comics?) {
        val position = myOnScrolledListener.position
        comics?.also {
            if (it.listComics.isNotEmpty()) {
                adapter?.setComics(it.listComics)
                binding.characterProfileTextViewComicsTitle.visibility = View.VISIBLE
            }
        }
        resetRecyclerView()
        binding.characterProfileRecyclerViewComics.scrollToPosition(position)
        setNotObservesVM()
    }

    private fun resetRecyclerView() {
        binding.characterProfileRecyclerViewComics.requestLayout()
        binding.characterProfileRecyclerViewComics.addOnScrollListener(myOnScrolledListener)
    }


    private fun setNotObservesVM() {
        viewModel.listComic.removeObservers(owner)
        viewModel.resetComics()
    }

    fun setAdapter() {
        adapter = CharacterProfileComicsListAdapter(arrayListOf(),this)
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
        binding.characterProfileRecyclerViewComics.addOnScrollListener(myOnScrolledListener)
    }

    fun getIntentExtras(argument: Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }

    fun searchWordBack() {
        searchTextViewAdapter?.also {
            if (it.getNumRecycler()==-1 || adapter!!.isInFirstPosition())
                it.backNumLine()
            if(it.getNumRecycler()!=-1)
            {
                setTextViewsColor()
                adapter?.backPosition()
            }
            else
            {
                setTextViewsColor()
                adapter?.setPosition(-1)
                moveToLine(it.numLine)
            }
        }
    }

    fun searchWordForward() {
        searchTextViewAdapter?.also {
            if (it.getNumRecycler()==-1 || adapter!!.isLastPosition())
                it.nextNumLine()
            if(it.getNumRecycler()!=-1) {
                setTextViewsColor()
                adapter?.nextPosition()
            }
            else
            {
                setTextViewsColor()
                adapter?.setPosition(-1)
                moveToLine(it.numLine)
            }
        }
    }

    fun searchText(text: String) {
        setSearchTextViewAdapter(text)
        setTextViewsColor()
        if(text.isNotEmpty()) {
            moveToLine(0)
        }
        adapter!!.fillItemsContainText(text)
    }

    private fun setTextViewsColor() {
        searchTextViewAdapter?.apply {
            setColorSearchTextFor(binding.characterProfileTextViewName, searchTextResultColor,
                selectSearchTextResultColor)
            setColorSearchTextFor(binding.characterProfileTextViewDescription, searchTextResultColor,
                selectSearchTextResultColor)
            setColorSearchTextFor(binding.characterProfileTextViewComicsTitle, searchTextResultColor,
                selectSearchTextResultColor)
        }
    }

    private fun setSearchTextViewAdapter(text: String) {
            searchTextViewAdapter =
                SearchTexTViewAdapter(
                    SearchTextResultUtils.createSearchTextResult(
                        text,
                        listOf(
                            binding.characterProfileTextViewName,
                            binding.characterProfileTextViewDescription,
                            binding.characterProfileTextViewComicsTitle
                        )
                    )
                )
            searchTextViewAdapter!!.addRecyclerPosition(binding.characterProfileRecyclerViewComics.id,4)
    }

    private fun moveToLine(numLine: Int) {
        searchTextViewAdapter?.searchText?.apply {
            if(encounter.size>0)
                encounter[numLine].apply {
                    binding.characterProfileScrollView.scrollTo(0,scrollPosition?:0)
                }
        }
    }

    fun returnNormalColor() {
        searchTextViewAdapter?.searchText?.search = ""
        adapter?.fillItemsContainText("")
        setTextViewsColor()
    }

    override fun onScroll(position: Int) {
        binding.characterProfileRecyclerViewComics.scrollToPosition(position)
    }
}