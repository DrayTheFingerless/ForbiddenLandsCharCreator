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
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.utils.Utils

class CharCreationViewModel(application: Application) : AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    private val character = MutableLiveData<FLCharacter>().apply {
        value = FLCharacter()
    }
    val char: LiveData<FLCharacter>
        get() = character


    private var listKinTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents("kin_talents")
    }
    val kTalents : LiveData<List<Talent>>
        get() = listKinTalents


    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents("profession_talents")
    }
    private var filterListProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value =  listOf()
    }
    val pTalents : LiveData<List<Talent>>
        get() = filterListProfessionTalents



    private val listGeneralTalents = MutableLiveData<List<Talent>>().apply {
        value =  loadTalents("general_talents")
    }
    val gTalents : LiveData<List<Talent>> = listGeneralTalents


    //function loads a list of Talents from json file
    private fun loadTalents(filename : String) : List<Talent>{
        val jsonFileString = Utils.getJsonDataFromAsset(this.getApplication(), filename)
        Log.i("talents", jsonFileString)

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        return gson.fromJson(jsonFileString, listType)
    }

    //filters from all profession talents into a filtered list for Prof Talents Spinner
    fun getFilteredProfessionTalents(profId : Int)  {
        Log.i("filtering talents", profId.toString())
        filterListProfessionTalents.value = listProfessionTalents.value?.filter{it.type == profId}
        print(filterListProfessionTalents.value?.count())
    }

    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}