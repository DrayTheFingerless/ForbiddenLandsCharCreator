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
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Profession
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.Utils.loadTalents


class CharCreationViewModel(application: Application) : AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    //Character to be saved
    private val character = MutableLiveData<FLCharacter>().apply { value = FLCharacter() }
    val char: LiveData<FLCharacter>
        get() = character
    //observer attribute fields
    val charStrength : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.Strength ?: 2}
    val charAgility : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.Agility ?: 2}
    val charWits : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.Wits ?: 2}
    val charEmpathy : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.Empathy ?: 2}


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

/*    fun ClearKin() {
        character.value?.Kin = null
    }*/

    fun SelectKin(position : Int){
        character.value?.UpdateKin(Kins.kins[position])
        kTName.value = kTalents.value?.first{ it.id == character.value?.Kin?.KinId }?.name ?: "None"
    }

    fun IncrementAttribute(idAttribute : Attributes)
    {
        character.value?.let{ch ->
            if(ch.AttributePoints > 0)
                when(idAttribute){
                    Attributes.Strength -> if(ch.Strength+1 >= ch.StrengthMax) {ch.Strength++
                        ch.AttributePoints--}
                    Attributes.Agility ->  if(ch.Agility+1 >= ch.AgilityMax) {ch.Agility++
                        ch.AttributePoints--}
                    Attributes.Wits -> if(ch.Wits+1 >= ch.WitsMax) {ch.Wits++
                        ch.AttributePoints--}
                    Attributes.Empathy ->  if(ch.Empathy+1 >= ch.EmpathyMax) { ch.Empathy++
                        ch.AttributePoints--}
                }
        }
    }

    fun DecrementAttribute(idAttribute : Attributes)
    {   character.value?.let{ch ->
            when(idAttribute){
                Attributes.Strength -> if(ch.Strength > 2) ch.Strength--
                Attributes.Agility ->  if(ch.Agility > 2) ch.Agility--
                Attributes.Wits -> if(ch.Wits > 2) ch.Wits--
                Attributes.Empathy ->  if(ch.Empathy > 2) ch.Empathy--
            }
        }
    }

    fun IncrementSkill(idSkill : Skills)
    {
        character.value?.let{ch ->
            ch.MySkills.get(idSkill)?.let {
                v ->
                ch.MySkills.put(idSkill, v+1);
            }
        }
    }
    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}