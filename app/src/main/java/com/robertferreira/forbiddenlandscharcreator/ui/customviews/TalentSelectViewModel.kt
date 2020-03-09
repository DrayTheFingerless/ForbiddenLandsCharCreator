package com.robertferreira.forbiddenlandscharcreator.ui.customviews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.Utils

class TalentSelectViewModel(application: Application) : AndroidViewModel(application) {

    //list of General Talents
    private val listGeneralTalents = MutableLiveData<List<Talent>>().apply {
        value = Utils.loadTalents(this@TalentSelectViewModel.getApplication(), "general_talents")
    }
    val gTalents: LiveData<List<Talent>> = listGeneralTalents

    private val _talentSelected = MutableLiveData<Talent>()
    val talentSelected: LiveData<Talent>
        get() = _talentSelected

    fun setTalent(itemId: Int)
    {
        _talentSelected.value = listGeneralTalents.value?.first { it.id == itemId }
    }
}