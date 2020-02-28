package com.robertferreira.forbiddenlandscharcreator.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to the Forbidden Lands! Under Construction"
    }
    val text: LiveData<String> = _text
}