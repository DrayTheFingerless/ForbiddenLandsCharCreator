package com.robertferreira.forbiddenlandscharcreator

open class Kin {
    var Name : String = ""
    var KinId : Int = -1
    var Description : String = ""

    constructor(name : String, id : Int, desc : String){
        Name = name
        KinId = id
        Description = desc
    }

    override fun toString(): String {
        return "$Name"
    }
}

object Kins {
    val kins = arrayListOf<Kin>(
        Kin("Human",0,"Humans are stinky and mean."),
        Kin("Elf",1,"Elves are kinda gay and pretty"),
        Kin("Half-Elf",2,"Half elves are kinda gay and pretty"),
        Kin("Dwarf",3,"Dwarves are short."),
        Kin("Halfling",4,"Degenerates."),
        Kin("Wolfkin",5,"No I'm NOT a furry!"),
        Kin("Orc",6,"Flashy bitz dem boyz has"),
        Kin("Goblin",7,"I dun like em"))

}