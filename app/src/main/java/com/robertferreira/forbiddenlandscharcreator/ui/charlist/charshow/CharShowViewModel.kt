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

    val name: LiveData<String> = Transformations.map(_character) { it.Name }
    val kin: LiveData<String> = Transformations.map(_character) {
        Kins.findKin(it.Kin)
    }
    val profession: LiveData<String> = Transformations.map(_character) {
        Professions.findProfession(it.Profession)
    }
    val age: LiveData<String> = Transformations.map(_character) {
          when(it.AgeId){
              0->  "Young ("+it.AgeNumber+")"
              1->  "Adult ("+it.AgeNumber+")"
              2->  "Old ("+it.AgeNumber+")"
              else -> ""
          }
    }
    val charStrength : LiveData<String> = Transformations.map(_character) { it.Strength.toString() }
    val charAgility : LiveData<String> = Transformations.map(_character) { it.Agility.toString() }
    val charWits : LiveData<String> =  Transformations.map(_character) { it.Wits.toString() }
    val charEmpathy : LiveData<String> = Transformations.map(_character) { it.Empathy.toString() }
    val charCurrentStrength : LiveData<String> = Transformations.map(_character) { it.CurrentStrength.toString() }
    val charCurrentAgility : LiveData<String> = Transformations.map(_character) { it.CurrentAgility.toString() }
    val charCurrentWits : LiveData<String> =  Transformations.map(_character) { it.CurrentWits.toString() }
    val charCurrentEmpathy : LiveData<String> = Transformations.map(_character) { it.CurrentEmpathy.toString() }

    val charMight : LiveData<String> = Transformations.map(_character) {
        it.MySkills.get(Skills.Might).toString() }
    val charEndurance : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Endurance) .toString()}
    val charCraft : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Crafting).toString()}
    val charMelee : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Melee).toString()}
    val charStealth : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Stealth).toString() }
    val charSleight : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.SleightOfHand).toString() }
    val charMove : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Move).toString() }
    val charMarksman : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Marksmanship).toString() }
    val charScout : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Scouting).toString() }
    val charLore : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Lore).toString() }
    val charSurvival : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Survival).toString() }
    val charInsight : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Insight).toString() }
    val charManipulation : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Manipulation).toString() }
    val charPerfomance : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Performance).toString() }
    val charHealing : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Healing).toString() }
    val charAnimal : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.AnimalHandling).toString() }

    //list of Profession Talents
    private var listProfessionTalents = MutableLiveData<List<Talent>>().apply {
        value = Utils.loadTalents(this@CharShowViewModel.getApplication(), "profession_talents")
    }

    var profTalent: LiveData<String> = Transformations.map(_character) {
        listProfessionTalents.value?.first{ talent ->
            talent.id == it.ProfessionTalent
        }?.name
    }

    var Pride = Transformations.map(_character) { it.Pride }
    var DarkSecret = Transformations.map(_character) { it.DarkSecret }
    var Body = Transformations.map(_character) { it.Body }
    var Face =Transformations.map(_character) { it.Face }
    var Clothing = Transformations.map(_character) { it.Clothing }

    init {
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun setCharacter(c: FLCharacter){
        _character.value = c
    }
}