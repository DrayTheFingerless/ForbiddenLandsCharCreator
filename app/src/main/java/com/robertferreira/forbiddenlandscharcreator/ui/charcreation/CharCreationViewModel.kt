package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.utils.Utils

class CharCreationViewModel(application: Application) : AndroidViewModel(application) {

    private val character = MutableLiveData<FLCharacter>().apply {
        value = FLCharacter()
    }
    val char: LiveData<FLCharacter> = character

    private val listKinTalents : MutableLiveData<List<Talent>> by lazy {
        MutableLiveData<List<Talent>>().also {
            loadKinTalents()
        }
    }
    private val listProfessionTalents : MutableLiveData<List<Talent>> by lazy {
        MutableLiveData<List<Talent>>().also {
            loadProfessionTalents()
        }
    }
    private val listGeneralTalents : MutableLiveData<List<Talent>> by lazy {
        MutableLiveData<List<Talent>>().also {
            loadGeneralTalents()
        }
    }

    private fun loadKinTalents(){
        val jsonFileString = Utils.getJsonDataFromAsset(this.getApplication(), "kin_talents.json")
        Log.i("kin talents", jsonFileString)

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        listKinTalents = gson.fromJson(jsonFileString, listType)
    }
    fun getKinTalents(): LiveData<List<Talent>> {
        return listKinTalents
    }

    private fun loadProfessionTalents(){
        val jsonFileString = Utils.getJsonDataFromAsset(this.getApplication(), "profession_talents.json")
        Log.i("profession talents", jsonFileString)

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        pTalents = gson.fromJson(jsonFileString, listType)
    }
    fun getProfessionTalents(): LiveData<List<Talent>> {
        return listProfessionTalents
    }

    private fun loadGeneralTalents(){
        val jsonFileString = Utils.getJsonDataFromAsset(this.getApplication(), "general_talents.json")
        Log.i("general talents", jsonFileString)

        val gson = Gson()
        val listType = object : TypeToken<List<Talent>>() {}.type

        gTalents = gson.fromJson(jsonFileString, listType)
    }
    fun getGeneralTalents(): LiveData<List<Talent>> {
        return listGeneralTalents
    }

    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}