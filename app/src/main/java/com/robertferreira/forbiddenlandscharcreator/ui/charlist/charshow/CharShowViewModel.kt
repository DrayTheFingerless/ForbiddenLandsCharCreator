package com.robertferreira.forbiddenlandscharcreator.ui.charlist.charshow

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.robertferreira.forbiddenlandscharcreator.*
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData

class CharShowViewModel(application: Application) : AndroidViewModel(application){
    private val _character = PropertyAwareMutableLiveData<FLCharacter>()
    val character
        get() = _character

    var name: LiveData<String> = Transformations.map(_character) {
        it.Name
    }
    var kin: LiveData<String> = Transformations.map(_character) {
        Kins.findKin(it.Kin)
    }
    var profession: LiveData<String> = Transformations.map(_character) {
        Professions.findProfession(it.Profession)
    }

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value = Utils.loadTalents(this@CharShowViewModel.getApplication(), "profession_talents")
    }

    var profTalent: LiveData<String> = Transformations.map(_character) {
        listProfessionTalents.value?.first{ talent ->
            talent.id == it.ProfessionTalent
        }?.name
    }

    init {

    }
    override fun onCleared() {
        super.onCleared()
    }

    fun setCharacter(c: FLCharacter){
        _character.value = c
    }
}