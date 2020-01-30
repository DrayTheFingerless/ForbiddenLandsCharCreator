package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharCreationViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is creation Fragment"
    }
    val text: LiveData<String> = _text
}