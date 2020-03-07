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

    val name: LiveData<String> = Transformations.map(_character) {
        it.Name
    }
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
    val charStrength : LiveData<String> = Transformations.map(_character) {
        it.Strength.toString() }
    val charAgility : LiveData<String> = Transformations.map(_character) { it.Agility.toString() }
    val charWits : LiveData<String> =  Transformations.map(_character) { it.Wits.toString() }
    val charEmpathy : LiveData<String> = Transformations.map(_character) { it.Empathy.toString() }


    val charMight : LiveData<String> = Transformations.map(_character) {
        it.MySkills.get(Skills.Might).toString() ?: "0"}
    val charEndurance : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Endurance) .toString()?: "0"}
    val charCraft : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Crafting).toString() ?: "0"}
    val charMelee : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Melee).toString() ?: "0"}
    val charStealth : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Stealth).toString() ?: "0"}
    val charSleight : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.SleightOfHand).toString() ?: "0"}
    val charMove : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Move).toString() ?: "0"}
    val charMarksman : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Marksmanship).toString() ?: "0"}
    val charScout : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Scouting).toString() ?: "0"}
    val charLore : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Lore).toString() ?: "0"}
    val charSurvival : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Survival).toString() ?: "0"}
    val charInsight : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Insight).toString() ?: "0"}
    val charManipulation : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Manipulation).toString() ?: "0"}
    val charPerfomance : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Performance).toString() ?: "0"}
    val charHealing : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.Healing).toString() ?: "0"}
    val charAnimal : LiveData<String> = Transformations.map(_character) { it.MySkills.get(Skills.AnimalHandling).toString() ?: "0"}

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