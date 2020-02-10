package com.robertferreira.forbiddenlandscharcreator

class Talent {

    var Name : String = ""
    var Description : String = ""

    var Rank1 : String = ""
    var Rank2 : String = ""
    var Rank3 : String = ""

    var Id : Int = -1

    constructor (){

    }

    constructor(id : Int, name : String, rank1 : String, rank2 : String, rank3 : String, desc : String){
        Id = id
        Name = name
        Description = desc
        Rank1 = rank1
        Rank2 = rank2
        Rank3 = rank3
    }

    override fun toString(): String {
        return "$Name"
    }

    fun getRankDesc(level : Int) : String {
        when(level){
            0 -> return Rank1
            1 -> return Rank2
            2 -> return Rank3
            else -> return ""
        }
    }
}