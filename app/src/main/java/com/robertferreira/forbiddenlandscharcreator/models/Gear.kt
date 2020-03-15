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

    val Equipped: Boolean = false,
    val Selected: Boolean = false,
    val Comment: String = "",
    //in Copper
    val Cost: Int = 0,
    val SkillMod : Int = 0,
    val AttributeMod: Int = 0,
    val ActionMod: Int = 0,
    val bonusType: Skills = Skills.Might){

    companion object{
        fun getWeapons(context: Context) : List<Weapon>{
            val type = object : TypeToken<List<Weapon>>() {}.type

            return creatJSONList(context,"weapons_gear", type)
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


enum class BonusType(i:Int){
    @SerializedName("0")
    Base(0),
    @SerializedName("1")
    Skill(1),
    @SerializedName("2")
    Gear(2),
    @SerializedName("3")
    Other(3)
}

enum class Weight(i : Int){
    @SerializedName("0")
   Tiny(0),
    @SerializedName("1")
    Light(1),
    @SerializedName("2")
    Medium(2),
    @SerializedName("3")
    Heavy(3),
    @SerializedName("4")
    Other(4)
}

enum class Range(i:Int){
    @SerializedName("0")
    ArmsLength(0),
    @SerializedName("1")
    Near(1),
    @SerializedName("2")
    Short(2),
    @SerializedName("3")
    Long(3)
}

enum class ItemType(i:Int){
    @SerializedName("0")
    Armor(0),
    @SerializedName("1")
    Helmet(1),
    @SerializedName("2")
    Shield(2),
    @SerializedName("3")
    Weapon(3),
    @SerializedName("4")
    Ranged(4),
    @SerializedName("5")
    Other(5)
}
