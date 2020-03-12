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
    val charStrength : LiveData<Int> = Transformations.map(_character) { it.Strength }
    val charAgility : LiveData<Int> = Transformations.map(_character) { it.Agility }
    val charWits : LiveData<Int> =  Transformations.map(_character) { it.Wits }
    val charEmpathy : LiveData<Int> = Transformations.map(_character) { it.Empathy }
    val charCurrentStrength : LiveData<Int> = Transformations.map(_character) { it.CurrentStrength }
    val charCurrentAgility : LiveData<Int> = Transformations.map(_character) { it.CurrentAgility }
    val charCurrentWits : LiveData<Int> =  Transformations.map(_character) { it.CurrentWits }
    val charCurrentEmpathy : LiveData<Int> = Transformations.map(_character) { it.CurrentEmpathy }

    val charMight : LiveData<Int> = Transformations.map(_character) {
        it.MySkills.get(Skills.Might) }
    val charEndurance : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Endurance)}
    val charCraft : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Crafting)}
    val charMelee : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Melee)}
    val charStealth : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Stealth) }
    val charSleight : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.SleightOfHand) }
    val charMove : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Move) }
    val charMarksman : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Marksmanship) }
    val charScout : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Scouting) }
    val charLore : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Lore) }
    val charSurvival : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Survival) }
    val charInsight : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Insight) }
    val charManipulation : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Manipulation) }
    val charPerfomance : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Performance) }
    val charHealing : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.Healing) }
    val charAnimal : LiveData<Int> = Transformations.map(_character) { it.MySkills.get(Skills.AnimalHandling) }

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

    fun addStrength(){
      _character.value?.let{
          if(it.Strength > it.CurrentStrength)
              it.CurrentStrength++
          it.notifyChange()
      }
    }
    fun addAgility(){
        _character.value?.let{
            if(it.Agility > it.CurrentAgility)
                it.CurrentAgility++
            it.notifyChange()

        }
    }
    fun addWits(){
        _character.value?.let{
            if(it.Wits > it.CurrentWits)
                it.CurrentWits++
            it.notifyChange()
        }
    }
    fun addEmpathy(){
        _character.value?.let{
            if(it.Empathy > it.CurrentEmpathy)
                it.CurrentEmpathy++
            it.notifyChange()
        }
    }

    fun removeStrength(){
        _character.value?.let{
            if(it.CurrentStrength> 0)
                it.CurrentStrength--
            it.notifyChange()
        }
    }
    fun removeAgility(){
        _character.value?.let{
            if(it.CurrentAgility> 0)
                it.CurrentAgility--
            it.notifyChange()
        }
    }
    fun removeWits(){
        _character.value?.let{
            if(it.CurrentWits> 0)
                it.CurrentWits--
            it.notifyChange()
        }
    }
    fun removeEmpathy(){
        _character.value?.let{
            if(it.CurrentEmpathy> 0)
                it.CurrentEmpathy--
            it.notifyChange()
        }
    }

    fun talentClicked(talentId : Int){
        //show a popup with talent info
        character.value?.TalentList?.first { it.id == talentId }?.let {
            tClicked.value = it
            showTalent.value = true
        }
    }
    fun addTClicked(talentId : Int) {
        character.value?.let{
            it.ChangeTalent(talentId, true)
        }
    }

    fun removeTClicked(talentId : Int) {
        character.value?.let{
            it.TalentList?.first{it.id == talentId}?.rankValue?.let{value ->
                if(value > 1)
                    it.ChangeTalent(talentId, false)
            }
        }
    }

    var showTalent = MutableLiveData<Boolean>().apply { value = false }
    val tClicked = MutableLiveData<Talent>()
}