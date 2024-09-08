package com.eliezer.marvel_characters.ui.fragments.character_profile.functionImp

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import com.eliezer.marvel_characters.BR
import com.eliezer.marvel_characters.core.utils.loadImageFromWebOperations
import com.eliezer.marvel_characters.data.configuration.searchTextResultColor
import com.eliezer.marvel_characters.data.configuration.selectSearchTextResultColor
import com.eliezer.marvel_characters.data.expand.indexOfEncounter
import com.eliezer.marvel_characters.data.repository.comics.mock.GetComicsRepository
import com.eliezer.marvel_characters.databinding.FragmentCharacterProfileBinding
import com.eliezer.marvel_characters.domain.SearchTextResultUtils
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
        colorAllLinesText(getTextView()!!.id,getTextView()!!.text.toString(),
            searchTextResultColor, selectSearchTextResultColor)
        moveToLine(numLine)
    }

    fun searchWordForward() {
        ++numLine
        colorAllLinesText(getTextView()!!.id,getTextView()!!.text.toString(),
            searchTextResultColor, selectSearchTextResultColor)
        moveToLine(numLine)
    }
    fun getTextView() : AppCompatTextView?
    {
        return when(searchText.encounter[numLine].idTextView)
        {
            binding.characterProfileTextViewName.id -> binding.characterProfileTextViewName
            binding.characterProfileTextViewDescription.id -> binding.characterProfileTextViewDescription
            binding.characterProfileTextViewComicsTitle.id -> binding.characterProfileTextViewComicsTitle
            else -> null
        }
    }

    fun searchWord(word: String) {
        searchText = SearchTextResultUtils.createSearchTextResult(word, listOf(
            binding.characterProfileTextViewName,
            binding.characterProfileTextViewDescription,
            binding.characterProfileTextViewDescription
        ))
        colorText(binding.characterProfileTextViewName)
        colorText(binding.characterProfileTextViewDescription)
        colorText(binding.characterProfileTextViewComicsTitle)
        moveToLine(0)
    }

    private fun colorText(textView: AppCompatTextView) {
        if (textView.text.contains(searchText.search)) {
            textView.apply {
                text =colorAllLinesText(id,text.toString(),  searchTextResultColor,selectSearchTextResultColor)
            }
        }
        else
            textView.apply {
                text = resetColor(text.toString(),searchTextResultColor)
            }
    }

    private fun moveToLine(numLine: Int) {
        binding.characterProfileScrollView.scrollTo(0, searchText.encounter[numLine].scrollPosition)
    }

    private fun colorUnderLineText(highlighted : SpannableStringBuilder,numLine: Int,text : String,@ColorInt color: Int) : SpannableStringBuilder {

        val position = text.indexOfEncounter(searchText.search,searchText.encounter[numLine].position)
        val intRange = IntRange(position,position+searchText.search.length-1)
        highlighted.setSpan(
            ForegroundColorSpan(color),
            intRange.first,
            intRange.last,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return highlighted
    }

    private fun colorAllLinesText(idTextView: Int,text : String,@ColorInt normalColor: Int,@ColorInt selectColor : Int) : SpannableStringBuilder{
        var highlighted = SpannableStringBuilder(text)
        for (i in 0..<searchText.encounter.size)
        {
            highlighted = if (i == numLine &&  idTextView == searchText.encounter[i].idTextView)
                colorUnderLineText(highlighted,i,text,normalColor)
            else if (idTextView == searchText.encounter[i].idTextView)
                colorUnderLineText(highlighted,i,text,selectColor)
            else
                highlighted
        }
        return highlighted
    }
    private fun resetColor(text : String,@ColorInt defaultColor: Int) : SpannableStringBuilder{
        val highlighted = SpannableStringBuilder(text)
        highlighted.setSpan(
            ForegroundColorSpan(defaultColor),
            0,
            text.length-1,
            SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return highlighted
    }
}