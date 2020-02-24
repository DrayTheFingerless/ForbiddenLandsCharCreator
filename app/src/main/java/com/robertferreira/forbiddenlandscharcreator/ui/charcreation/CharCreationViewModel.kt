package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.Utils.loadTalents
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData


class CharCreationViewModel(application: Application) : AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    //Character to be saved
    private val character = PropertyAwareMutableLiveData<FLCharacter>().apply { value = FLCharacter() }
    val char: LiveData<FLCharacter>
        get() = character

    //observer attribute fields
    val charStrength : LiveData<Int>
        get() = Transformations.map(character) { it.Strength }
    val charAgility : LiveData<Int>
        get() = Transformations.map(character) { it.Agility }
    val charWits : LiveData<Int>
        get() =  Transformations.map(character) { it.Wits }
    val charEmpathy : LiveData<Int>
        get() = Transformations.map(character) { it.Empathy }
    //observer skill fields
    val charMight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills?.get(Skills.Might) ?: 0}
    val charEndurance : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Endurance) ?: 2}
    val charCraft : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Crafting) ?: 2}
    val charMelee : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Melee) ?: 2}
    val charStealth : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Stealth) ?: 2}
    val charSleight : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.SleightOfHand) ?: 2}
    val charMove : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Move) ?: 2}
    val charMarksman : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Marksmanship) ?: 2}
    val charScout : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Scouting) ?: 2}
    val charLore : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Lore) ?: 2}
    val charSurvival : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Survival) ?: 2}
    val charInsight : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Insight) ?: 2}
    val charManipulation : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Manipulation) ?: 2}
    val charPerfomance : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.Performance) ?: 2}
    val charHealing : LiveData<Int>
        get() = MutableLiveData<Int>().apply{
            value = character.value?.MySkills?.get(Skills.Healing) ?: 2}
    val charAnimal : LiveData<Int>
        get() = MutableLiveData<Int>().apply{value = character.value?.MySkills?.get(Skills.AnimalHandling) ?: 2}

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

    fun SelectKin(position : Int){
        character.value?.UpdateKin(Kins.kins[position])
        kTName.value = kTalents.value?.first{ it.id == character.value?.Kin?.KinId }?.name ?: "None"
    }

    fun SelectProfession(position : Int){
        character.value?.let{ch->
            getFilteredProfessionTalents(position)
            ch.UpdateProfession(Professions.professions[position])
        }
    }

    fun SelectAge(position : Int, value : Int){
        character.value?.let{ch->
            ch.UpdateAge(position, value)
        }
    }

    fun IncrementAttribute(idAttribute : Attributes)
    {
        character.value?.let { ch ->
            ch.IncrementAttribute(idAttribute)
        }
    }

    fun DecrementAttribute(idAttribute : Attributes)
    {   character.value?.let{ch ->
           ch.DecrementAttribute(idAttribute)
        }
    }

    fun ChangeSkill(idSkill : Skills, addOrNot : Boolean)
    {
        character.value?.let{ch ->
            ch.ChangeSkill(idSkill, addOrNot)
        }
    }
    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}