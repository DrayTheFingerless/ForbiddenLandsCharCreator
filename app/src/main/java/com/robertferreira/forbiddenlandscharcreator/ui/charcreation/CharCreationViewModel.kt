package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robertferreira.forbiddenlandscharcreator.FLCharacter

class CharCreationViewModel : ViewModel() {
    private val character = MutableLiveData<FLCharacter>().apply {
        value = FLCharacter()
    }

    val char: LiveData<FLCharacter> = character


}