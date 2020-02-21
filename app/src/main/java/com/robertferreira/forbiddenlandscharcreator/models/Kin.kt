package com.robertferreira.forbiddenlandscharcreator

import org.w3c.dom.Attr


data class Kin (val Name : String, val KinId : Int, val KeyAttribute: Attributes, val Description : String) {

    override fun toString(): String {
        return "$Name"
    }

}

object Kins {
    val kins = arrayListOf<Kin>(
        Kin("Human",0,Attributes.Empathy,"Humans are stinky and mean."),
        Kin("Elf",1, Attributes.Agility,"Elves are kinda gay and pretty"),
        Kin("Half-Elf",2, Attributes.Wits,"Half elves are kinda gay and pretty"),
        Kin("Dwarf",3,Attributes.Strength,"Dwarves are short."),
        Kin("Halfling",4, Attributes.Empathy,"Degenerates."),
        Kin("Wolfkin",5, Attributes.Agility,"No I'm NOT a furry!"),
        Kin("Orc",6, Attributes.Strength,"Flashy bitz dem boyz has"),
        Kin("Goblin",7, Attributes.Agility,"I dun like em"))

}

