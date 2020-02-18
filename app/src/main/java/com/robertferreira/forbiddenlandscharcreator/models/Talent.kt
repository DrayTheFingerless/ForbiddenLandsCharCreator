package com.robertferreira.forbiddenlandscharcreator

open class Talent {

    var name : String = ""
    var description : String = ""

    var rank1 : String = ""
    var rank2 : String = ""
    var rank3 : String = ""
    var type : Int = -1
    var id : Int = -1

    constructor (){

    }

    constructor(name : String,  desc : String, rank1 : String?, rank2 : String?, rank3 : String?, type : Int?, id : Int?){
        this.name = name
        this.description = desc
        if (rank1 != null) this.rank1 = rank1
        if (rank2 != null) this.rank2 = rank2
        if (rank3 != null) this.rank3 = rank3
        if (type != null) this.type = type
        if (id != null) this.id = id

    }

    override fun toString(): String {
        return "$name"
    }

    fun getRankDesc(level : Int) : String {
        when(level){
            0 -> return rank1
            1 -> return rank2
            2 -> return rank3
            else -> return ""
        }
    }
}