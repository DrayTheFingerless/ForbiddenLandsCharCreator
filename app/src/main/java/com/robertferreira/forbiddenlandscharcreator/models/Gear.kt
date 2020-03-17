package com.robertferreira.forbiddenlandscharcreator.models

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.robertferreira.forbiddenlandscharcreator.Skills
import com.robertferreira.forbiddenlandscharcreator.Utils.creatJSONList


open class Gear(
    val id: Int,
    val name: String,
    val weight: Weight,
    val bonus: Int,
    val gearType: ItemType,
    val features: List<String>,

    val equipped: Boolean = false,
    val selected: Boolean = false,
    val comment: String = "",
    //in Copper
    val cost: Int = 0,
    val skillMod : Int = 0,
    val attributeMod: Int = 0,
    val actionMod: Int = 0,
    val bonusType: Skills = Skills.Might){

    companion object{
        fun getGear(context: Context) : List<Gear>{
            val type = object : TypeToken<List<Gear>>() {}.type

            return creatJSONList(context,"general_gear", type)
        }
    }
}


class Weapon (id: Int,
             name: String,
             weight: Weight,
             bonus: Int,
             bonusType: Skills,
             val twoHanded: Boolean,
             val damage: Int,
             val range: Range,
             features : List<String>,
             gearType: ItemType = ItemType.Weapon,
             cost : Int
             ) : Gear(id,name, weight,bonus,gearType,features){

    companion object{
        fun getWeapons(context: Context) : List<Weapon>{
            val type = object : TypeToken<List<Weapon>>() {}.type

            return creatJSONList(context,"weapons_gear", type)
        }
    }
}

class Armour(id: Int,
            name: String,
            weight: Weight,
            bonus: Int,
            features : List<String>,
            gearType: ItemType,
             cost: Int
) : Gear(id,name, weight,bonus,gearType,features) {
    companion object{
        fun getArmours(context: Context) : List<Armour>{
            val type = object : TypeToken<List<Armour>>() {}.type

            var list =  creatJSONList<List<Armour>>(context,"armor_gear", type)
            return list
        }
    }
}

class Shield(id: Int,
            name: String,
            weight: Weight,
            bonus: Int,
            features : List<String>,
            gearType: ItemType = ItemType.Shield) : Gear(id,name, weight,bonus,gearType,features) {
}


enum class BonusType{
    Base,
    Skill,
    Gear,
    Other
}

enum class Weight{
    Tiny,
    Light,
    Medium,
    Heavy,
    Other
}

enum class Range{
    ArmsLength,
    Near,
    Short,
    Long,
    Distant
}

enum class ItemType{
    Armor,//0
    Helmet,
    Shield,
    Weapon,
    Ranged,
    Other
}
