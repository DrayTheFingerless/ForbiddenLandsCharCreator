package com.robertferreira.forbiddenlandscharcreator



class Kin {
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
    val kins = arrayListOf<Kin>(Kin("Human",1,"Humans are stinky and mean."),
    Kin("Elf",2,"Elves are kinda gay and pretty"),
    Kin("Dwarf",3,"Dwarves are short."),
    Kin("Goblin",4,"Goblins are creepy and funny"))
}