package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robertferreira.forbiddenlandscharcreator.Attributes
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Kins
import com.robertferreira.forbiddenlandscharcreator.Professions
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Talent
import com.robertferreira.forbiddenlandscharcreator.Utils.loadTalents
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData


class CharCreationViewModel(application: Application, character: FLCharacter) : AndroidViewModel(application) {

    /*@OnLifecycleEvent(Lifecycle.Event.)
    fun onResume() {  }*/

    //Character to be saved
    private val character = PropertyAwareMutableLiveData<FLCharacter>().apply{ value = character }
    val char: LiveData<FLCharacter>
        get() = character

    //observer attribute fields
    val attrPoints : LiveData<Int>
        get () = Transformations.map(character) {val i = when(it.AgeId){
            0-> 15 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            1-> 14 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            2-> 13 - (it.Strength+it.Agility+it.Wits+it.Empathy)
            else-> 13 - (it.Strength+it.Agility+it.Wits+it.Empathy)
        }
            i
    }
    val charStrength : LiveData<Int>
        get() = Transformations.map(character) { it.Strength }
    val charAgility : LiveData<Int>
        get() = Transformations.map(character) { it.Agility }
    val charWits : LiveData<Int>
        get() =  Transformations.map(character) { it.Wits }
    val charEmpathy : LiveData<Int>
        get() = Transformations.map(character) { it.Empathy }

    //observer skill fields
    val skillPoints : LiveData<Int>
        get () = Transformations.map(character) {val i = when(it.AgeId){
            0-> 8 - it.MySkills?.values.sum()
            1-> 10 - it.MySkills?.values.sum()
            2-> 12 - it.MySkills?.values.sum()
            else-> 8 - it.MySkills?.values.sum()
        }
            i
        }
    val charMight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Might) ?: 0}
    val charEndurance : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Endurance) ?: 0}
    val charCraft : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Crafting) ?: 0}
    val charMelee : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Melee) ?: 0}
    val charStealth : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Stealth) ?: 0}
    val charSleight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.SleightOfHand) ?: 0}
    val charMove : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Move) ?: 0}
    val charMarksman : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Marksmanship) ?: 0}
    val charScout : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Scouting) ?: 0}
    val charLore : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Lore) ?: 0}
    val charSurvival : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Survival) ?: 0}
    val charInsight : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Insight) ?: 0}
    val charManipulation : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Manipulation) ?: 0}
    val charPerfomance : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Performance) ?: 0}
    val charHealing : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.Healing) ?: 0}
    val charAnimal : LiveData<Int>
        get() = Transformations.map(character) { it.MySkills.get(Skills.AnimalHandling) ?: 0}

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


    init{

    }

    //filters from all profession talents into a filtered list for Prof Talents Spinner
    fun getFilteredProfessionTalents(profId : Int)  {
        Log.i("filtering talents", profId.toString())
        filterListProfessionTalents.value = listProfessionTalents.value?.filter{it.type == profId}
        print(filterListProfessionTalents.value?.count())
    }

    fun SelectKin(position : Int){
        character.value?.UpdateKin(position)
        kTName.value = kTalents.value?.first{ it.id == character.value?.Kin }?.name ?: "None"
    }

    fun SelectProfession(position : Int){
        character.value?.let{ch->
            getFilteredProfessionTalents(position)
            ch.UpdateProfession(position)
        }
    }

    fun SelectAge(position : Int, value : Int){
        character.value?.let{ch->
            ch.UpdateAge(position, value)
        }
    }

    fun IncrementAttribute(idAttribute : Attributes)
    {
        character.value?.let {
            if(it.AttrPointsLeft() > 0)
                it.IncrementAttribute(idAttribute)
        }
    }

    fun DecrementAttribute(idAttribute : Attributes)
    {   character.value?.let{
           it.DecrementAttribute(idAttribute)
        }
    }

    fun ChangeSkill(idSkill : Skills, addOrNot : Boolean)
    {
        character.value?.let {
            it.ChangeSkill(idSkill, addOrNot)
        }
    }
    //bindable livedata variable
/*    private val text = MutableLiveData<String>().apply {
        value = ""
    }
    val Text : LiveData<String> = text*/
}

class CharCreationViewModelFactory(private val application : Application, private val char: FLCharacter) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharCreationViewModel::class.java)) {
            return CharCreationViewModel(application, char) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}