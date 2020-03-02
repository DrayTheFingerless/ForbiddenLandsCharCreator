package com.robertferreira.forbiddenlandscharcreator.ui.charcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.robertferreira.forbiddenlandscharcreator.FLCharacter
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.utils.PropertyAwareMutableLiveData

class SkillCreationViewModel : ViewModel() {

    //Character to be saved
    private val character = PropertyAwareMutableLiveData<FLCharacter>().apply { value = FLCharacter() }
    val char: LiveData<FLCharacter>
        get() = character

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

    fun ChangeSkill(idSkill : Skills, addOrNot : Boolean)
    {
        character.value?.let {
            it.ChangeSkill(idSkill, addOrNot)
        }
    }

    fun setCharacter(c : FLCharacter){
        character.value = c
    }
}
