package com.robertferreira.forbiddenlandscharcreator

open class Talent {

    var name : String = ""
    var description : String = ""

    var rank_1 : String = ""
    var rank_2 : String = ""
    var rank_3 : String = ""
    var type : Int = -1
    var id : Int = -1

    var rankValue : Int = 0

    constructor (){

    }

    constructor(name : String,  desc : String, rank1 : String?, rank2 : String?, rank3 : String?, type : Int?, id : Int?){
        this.name = name
        this.description = desc
        rank1?.let{
            rank_1 = it }
        rank2?.let{ rank_2 = it }
        rank3?.let{ rank_3 = it }
        type?.let{ this.type = it }
        id?.let{ this.id = it }

    }

    override fun toString(): String {
        return "$name"
    }

    fun getRankDesc(level : Int) : String {
        when(level){
            0 -> return rank_1
            1 -> return rank_2
            2 -> return rank_3
            else -> return ""
        }
    }

    fun increaseRank()
    {
        if(rankValue<3)
        rankValue++
    }

    fun decreaseRank(){
        if(rankValue>0)
        rankValue--
    }
}