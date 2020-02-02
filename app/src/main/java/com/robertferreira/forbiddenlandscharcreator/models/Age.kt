package com.robertferreira.forbiddenlandscharcreator

class Age {
    var Name : String = ""
    var AgeId : Int = -1
    var NumAttributes : Int = 0
    var NumSkills : Int = 0
    var NumTalents : Int = 0

    constructor(name : String, id : Int, attr : Int, skills : Int, talents : Int){
        Name = name
        AgeId = id
        NumAttributes = attr
        NumSkills = skills
        NumTalents = talents
    }

    override fun toString(): String {
        return "$Name"
    }


}

object Ages {
    val ages = arrayListOf<Age>(Age("Young",1,15, 8, 1),
        Age("Adult",2,14,10,2),
        Age("Old",3, 13,12,3))
}