package com.veryable.android.controllers

import android.view.View
import com.veryable.android.databinding.ActionBarBinding

class HeaderControl(private val binder: ActionBarBinding) {

    fun updateHeaderText(title: String?) {
        binder.labelTitle.text = title
    }

    val headerText: String
        get() = binder.labelTitle.text.toString()

    fun enableBackButton(flag: Boolean) {
        binder.btnBack.visibility = if (flag) View.VISIBLE else View.INVISIBLE
        binder.btnBack.isClickable = flag
    }
}