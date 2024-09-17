package com.eliezer.marvel_search_api.domain.listener

import android.text.Editable
import android.text.TextWatcher

class MyTextChangedListener(
   private val beforeTextChanged : ((p0: CharSequence?, p1: Int, p2: Int, p3: Int) -> Unit)?,
    private val onTextChanged: ((p0: CharSequence?, p1: Int, p2: Int, p3: Int) -> Unit)?,
    private val afterTextChanged: ((p0: Editable?) -> Unit)?
) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        beforeTextChanged?.invoke(p0,p1,p2,p3)
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged?.invoke(p0,p1,p2,p3)
    }

    override fun afterTextChanged(p0: Editable?) {
        afterTextChanged?.invoke(p0)
    }
}