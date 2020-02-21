package com.robertferreira.forbiddenlandscharcreator


class Profession (val Name : String, val ProfessionId : Int, val KeyAttribute : Attributes, val Skills : List<Skills>, val Description : String){

    override fun toString(): String {
        return "$Name"
    }
}

object Professions {
    val professions = arrayListOf<Profession>(
        Profession("Druid",0, Attributes.Wits, listOf(Skills.Endurance, Skills.Survival, Skills.Insight, Skills.Healing, Skills.AnimalHandling),"Registered animal lover"),
        Profession("Fighter",1, Attributes.Strength, listOf(Skills.Might, Skills.Endurance, Skills.Melee, Skills.Crafting, Skills.Move),"Big Sticks"),
        Profession("Hunter",2, Attributes.Agility, listOf(Skills.Stealth, Skills.Move, Skills.Marksmanship, Skills.Scouting, Skills.Survival),"Loner in the woods"),
        Profession("Minstrel",3, Attributes.Empathy, listOf(Skills.Lore, Skills.Insight, Skills.Manipulation, Skills.Performance, Skills.Healing),"Plays the guitar"),
        Profession("Peddler",4, Attributes.Empathy, listOf(Skills.Crafting, Skills.SleightOfHand, Skills.Scouting, Skills.Insight, Skills.Manipulation),"I'll buy it at a high price"),
        Profession("Rider",5, Attributes.Agility, listOf(Skills.Endurance, Skills.Melee, Skills.Marksmanship, Skills.Survival, Skills.AnimalHandling),"My little pony"),
        Profession("Rogue",6, Attributes.Agility, listOf(Skills.Melee, Skills.Stealth, Skills.SleightOfHand, Skills.Move, Skills.Manipulation),"That's mine"),
        Profession("Sorceror",7, Attributes.Wits, listOf(Skills.Crafting, Skills.SleightOfHand, Skills.Lore, Skills.Insight, Skills.Manipulation),"What a fuckin nerd"))

}