package com.robertferreira.forbiddenlandscharcreator

import com.robertferreira.forbiddenlandscharcreator.models.Gear
import com.robertferreira.forbiddenlandscharcreator.models.Weapon

class Profession (val Name : String, val ProfessionId : Int, val KeyAttribute : Attributes, val Skills : List<Skills>, val Description : String,
                val Food: Int,
                val Water: Int,
                val Torch: Int,
                val Arrows: Int,
                  val Silver: Int,
                  val Gear: List<Gear>){
    //0 = none, 1 = d6, 2 = d8, 3 = d10, 4 = d12
    //
    override fun toString(): String {
        return "$Name"
    }
}

object Professions {
    val professions = arrayListOf<Profession>(
        Profession("Druid",0, Attributes.Wits, listOf(Skills.Endurance, Skills.Survival, Skills.Insight, Skills.Healing, Skills.AnimalHandling),"Registered animal lover",
        2,2,0,0,1,
            listOf()),
        Profession("Fighter",1, Attributes.Strength, listOf(Skills.Might, Skills.Endurance, Skills.Melee, Skills.Crafting, Skills.Move),"Big Sticks",
            2,1,0,0,1,
            listOf()),
        Profession("Hunter",2, Attributes.Agility, listOf(Skills.Stealth, Skills.Move, Skills.Marksmanship, Skills.Scouting, Skills.Survival),"Loner in the woods",
            2,2,0,3,1,
            listOf()),
        Profession("Minstrel",3, Attributes.Empathy, listOf(Skills.Lore, Skills.Insight, Skills.Manipulation, Skills.Performance, Skills.Healing),"Plays the guitar",
            2,1,0,0,2,
            listOf()),
        Profession("Peddler",4, Attributes.Empathy, listOf(Skills.Crafting, Skills.SleightOfHand, Skills.Scouting, Skills.Insight, Skills.Manipulation),"I'll buy it at a high price",
            2,2,0,0,4,
            listOf()),
        Profession("Rider",5, Attributes.Agility, listOf(Skills.Endurance, Skills.Melee, Skills.Marksmanship, Skills.Survival, Skills.AnimalHandling),"My little pony",
            2,2,0,3,1,
            listOf()),
        Profession("Rogue",6, Attributes.Agility, listOf(Skills.Melee, Skills.Stealth, Skills.SleightOfHand, Skills.Move, Skills.Manipulation),"That's mine",
            1,1,0,0,3,
            listOf()),
        Profession("Sorcerer",7, Attributes.Wits, listOf(Skills.Crafting, Skills.SleightOfHand, Skills.Lore, Skills.Insight, Skills.Manipulation),"What a fuckin nerd",
            1,2,0,0,2,
            listOf()))

    fun findProfession(profId:Int): String{
        return professions.first{
            it.ProfessionId == profId
        }.toString()
    }
}