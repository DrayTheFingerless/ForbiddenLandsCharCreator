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
    val professions = arrayListOf<Profession>(
        Profession("Druid",0,"Registered animal lover"),
        Profession("Fighter",1,"Big Sticks"),
        Profession("Hunter",2,"Loner in the woods"),
        Profession("Minstrel",3,"Plays the guitar"),
        Profession("Peddler",4,"I'll buy it at a high price"),
        Profession("Rider",5,"My little pony"),
        Profession("Rogue",6,"That's mine"),
        Profession("Sorceror",7,"What a fuckin nerd"))

}