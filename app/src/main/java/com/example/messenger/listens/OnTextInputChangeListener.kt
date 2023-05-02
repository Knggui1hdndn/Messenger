package com.example.messenger.listens

import com.google.android.material.textfield.TextInputLayout

interface OnTextInputChangeListener {
    fun onTextChange(vararg textInputLayout: TextInputLayout, callback: (String,Int) -> Unit)
}