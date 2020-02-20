package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.utils.Utils
import com.robertferreira.forbiddenlandscharcreator.utils.Utils.loadTalents

class CharCreationViewModel(application: Application) : AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    //Character to be saved
    private val character = MutableLiveData<FLCharacter>().apply { value = FLCharacter() }
    val char: LiveData<FLCharacter>
        get() = character

    private val kTName = MutableLiveData<String>()
    val kinTalentName : LiveData<String>
        get() = kTName


    //list of Kin Talents
    private var listKinTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"kin_talents") }
    val kTalents : LiveData<List<Talent>>
        get() = listKinTalents

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"profession_talents") }

    //list of Profession Talents Filtered by Profession selected
    private var filterListProfessionTalents = MutableLiveData<List<Talent>>().apply { value =  listOf() }
    val pTalents : LiveData<List<Talent>>
        get() = filterListProfessionTalents


    //list of General Talents
    private val listGeneralTalents = MutableLiveData<List<Talent>>().apply { value =  loadTalents(this@CharCreationViewModel.getApplication(),"general_talents") }
    val gTalents : LiveData<List<Talent>> = listGeneralTalents



    //filters from all profession talents into a filtered list for Prof Talents Spinner
    fun getFilteredProfessionTalents(profId : Int)  {
        Log.i("filtering talents", profId.toString())
        filterListProfessionTalents.value = listProfessionTalents.value?.filter{it.type == profId}
        print(filterListProfessionTalents.value?.count())
    }

    fun ClearKin() {
        character.value?.KinId = -1
    }
    fun SelectKin(position : Int){
        character.value?.KinId = Kins.kins[position].KinId
        kTName.value = kTalents.value?.first{ it.id == character.value?.KinId }?.name
    }

    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}