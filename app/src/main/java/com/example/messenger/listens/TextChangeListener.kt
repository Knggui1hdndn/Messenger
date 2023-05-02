package com.example.messenger.listens

import android.annotation.SuppressLint
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

open class TextChangeListener() : OnTextInputChangeListener {

    @SuppressLint("NotConstructor")
    override fun onTextChange(vararg textInputLayout: TextInputLayout, callback: (String,Int) -> Unit) {
        for (i in textInputLayout.iterator()) {
            i.editText?.addTextChangedListener(onTextChanged = { t, _, _, count ->
                callback(t.toString(),i.id)
             })
        }
    }

}