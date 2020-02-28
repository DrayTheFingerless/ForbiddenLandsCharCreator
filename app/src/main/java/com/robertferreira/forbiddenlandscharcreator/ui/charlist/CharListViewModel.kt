package com.robertferreira.forbiddenlandscharcreator.ui.charlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is list of characters. Under Construction."
    }
    val text: LiveData<String> = _text
}