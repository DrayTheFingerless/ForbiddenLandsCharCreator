package com.robertferreira.forbiddenlandscharcreator

open class Talent {

    var Name : String = ""
    var Description : String = ""

    var Rank1 : String = ""
    var Rank2 : String = ""
    var Rank3 : String = ""
    var Type : Int = -1
    var Id : Int = -1

    constructor (){

    }

    constructor(name : String, rank1 : String, rank2 : String, rank3 : String, desc : String, type : Int?, id : Int?){
        Name = name
        Description = desc
        Rank1 = rank1
        Rank2 = rank2
        Rank3 = rank3
        if (type != null) {
            Type = type
        }

        if (id != null) {
            Id = id
        }

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