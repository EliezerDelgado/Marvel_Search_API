package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.expand.indexOfEncounter
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.domain.listener.MyOnScrolledListener
import com.eliezer.marvel_characters.models.SearchEncounter
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
) {
    private var character: Character? = null
    private var adapter: CharacterProfileComicsListAdapter? = null
    private val myOnScrolledListener = MyOnScrolledListener { getListComics() }
    private var searchText = SearchTextResult()
    private var numLine = 0

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
        adapter = CharacterProfileComicsListAdapter(arrayListOf())
        binding.characterProfileRecyclerViewComics.setHasFixedSize(true)
        binding.characterProfileRecyclerViewComics.adapter = adapter
        binding.characterProfileRecyclerViewComics.addOnScrollListener(myOnScrolledListener)
    }

    fun getIntentExtras(argument: Bundle) {
        character = CharacterProfileFragmentArgs.fromBundle(argument).argCharacter
    }

    fun searchWordBack() {
        --numLine
        colorAllLinesText(searchText.encounter[numLine].layout,"red","blue")
        moveToLine(numLine)
    }

    fun searchWordForward() {
        ++numLine
        colorAllLinesText(searchText.encounter[numLine].layout,"red","blue")
        moveToLine(numLine)
    }

    fun searchWord(word: String) {
        searchText.encounter.clear()
        searchText.search = word
        colorText(binding.characterProfileTextViewName)
        colorText(binding.characterProfileTextViewDescription)
        colorText(binding.characterProfileTextViewComicsTitle)
    }

    private fun colorText(textView: AppCompatTextView) {
        if (textView.text.contains(searchText.search)) {
            fillOutListIndexOfEncountersWord(textView)
            colorAllLinesText(textView,"red","blue")
            moveToLine(0)
        }
        else
             colorAllLinesText(textView,"white","white")
    }

    private fun moveToLine(numLine: Int) {
        binding.characterProfileScrollView.scrollTo(0, searchText.encounter[numLine].scrollPosition)
    }

    private fun colorUnderLineText(numLine: Int,text : String, color: String) : String {
        val highlighted = "<font color='$color'><u>${searchText.search}</u></font>"
        val position = text.indexOfEncounter(searchText.search,searchText.encounter[numLine].position)
        val intRange = IntRange(position,position+searchText.search.length-1)
        return text.replaceRange(intRange, highlighted)
    }

    private fun colorAllLinesText(textView: AppCompatTextView, normalColor: String,selectColor : String){
        var text = textView.text.toString()
        for (i in 0..<searchText.encounter.size)
        {
            text = if (i == numLine && textView.id == searchText.encounter[i].layout.id)
                colorUnderLineText(i,text,selectColor)
            else if (textView.id == searchText.encounter[i].layout.id)
                colorUnderLineText(i,text,normalColor)
            else
                text
        }
        setTextColor(textView,text)
    }
    private fun setTextColor(textView: AppCompatTextView,newText : String)
    {
        textView.text = Html.fromHtml(newText, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    private fun fillOutListIndexOfEncountersWord(textView: AppCompatTextView) {
        searchText.apply {
            var position = textView.text.indexOf(search, 0)
            var index = 0
            while (position != -1) {
                encounter.add(SearchEncounter(textView,index++))
                val startIndex =  position +1
                position = textView.text.indexOf(search,startIndex)
            }
        }
    }
}