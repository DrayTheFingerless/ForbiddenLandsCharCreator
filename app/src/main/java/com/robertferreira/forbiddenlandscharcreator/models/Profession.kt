package com.robertferreira.forbiddenlandscharcreator

class Profession {
    var Name : String = ""
    var ProfessionId : Int = -1
    var Description : String = ""

    constructor(name : String, id : Int, desc : String){
        Name = name
        ProfessionId = id
        Description = desc
    }
    override fun toString(): String {
        return "$Name"
    }
}

object Professions {
    val professions = arrayListOf<Profession>(Profession("Fighter",1,"Big Sticks"),
        Profession("Hunter",2,"Loner in the woods"),
        Profession("Druid",3,"Registered animal lover"),
        Profession("Sorceror",4,"What a fuckin' nerd"))
}